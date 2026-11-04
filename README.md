# ERP para la Bodega de Alimentos "Frescura Total"

## Descripción
Aplicación de consola en Java que centraliza la operación de la bodega de alimentos
"Frescura Total". Resuelve las pérdidas por vencimiento, la falta de trazabilidad, el
desorden de inventario y la facturación manual, conectando cuatro módulos: **inventario y
calidad** (recepción de mercancía por lotes, alertas de vencimiento, cuarentena,
inspección y mermas), **proveedores y compras** (directorio, catálogo e historial de
entregas), **ventas y facturación** (clientes mayoristas y minoristas, venta con
estrategia FEFO, promoción automática del 50 % para lotes próximos a vencer, descuento
automático de stock y factura electrónica en PDF con CUFE y código QR simulados) y
**logística y entregas** (conductores, vehículos, panel de despachos, rutas de entrega y
seguimiento del pedido hasta su entrega).

## Autores
- Juan David Navas Jurado

## Requerimientos Previos
- **Java JDK 17**
- **Apache Maven 3.8+** (incluido en Apache NetBeans)
- **MySQL Server 8.0**
- **Apache NetBeans IDE 12 o superior**
- Un cliente de MySQL para ejecutar el script (MySQL Workbench, DBeaver o la consola `mysql`)
- **Git**

Las librerías externas se descargan automáticamente con Maven:

| Librería | Uso |
|---|---|
| `mysql-connector-j` 8.4.0 | Conexión JDBC con MySQL |
| `openpdf` 2.0.3 | Generación de la factura en PDF |
| `zxing core / javase` 3.5.3 | Generación del código QR de la factura |

## Configuración de la Base de Datos
1. Iniciar el servidor de MySQL 8 en local.
2. Abrir el script **`docs/database.sql`** en MySQL Workbench o DBeaver y **ejecutarlo
   completo**. Desde la consola también se puede hacer así:
   ```bash
   mysql -u root -p < docs/database.sql
   ```
   El script:
   - borra y crea la base de datos `erp_frescura_total` (sección **DDL**),
   - crea las 22 tablas con sus relaciones,
   - carga los catálogos y los datos de prueba coherentes (sección **DML**: 50 proveedores,
     60 productos, 55 clientes, 80 lotes, 60 ventas con sus detalles y facturas, rutas, etc.).

   > ⚠️ El script comienza con `DROP DATABASE IF EXISTS`, así que borra cualquier versión
   > anterior de `erp_frescura_total`.

3. Configurar las credenciales en la clase
   `src/main/java/com/frescuratotal/erp/common/DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/erp_frescura_total";
   private static final String USER = "root";
   private static final String PASSWORD = "tu_contraseña";
   ```

## Instalación y Ejecución
### Clonar el repositorio
```bash
git clone https://github.com/DavidNavas898/frescura-total-erp.git
```

### Abrir en Apache NetBeans
1. **File > Open Project** y seleccionar la carpeta `frescura-total-erp`. NetBeans la
   reconoce como proyecto Maven por el archivo `pom.xml`.
2. Esperar a que Maven descargue las dependencias (se ve en la barra inferior).

### Compilar
- Menú **Run > Clean and Build Project** (o **Shift + F11**).

### Ejecutar
- Menú **Run > Run Project** (o **F6**). La clase principal es `com.frescuratotal.erp.Main`.
- La aplicación muestra el menú principal en la consola de salida:
  ```
  === FRESCURA TOTAL ERP ===
  1. Proveedores
  2. Productos
  3. Inventario (lotes)
  4. Clientes
  5. Ventas
  6. Logistica y entregas
  0. Salir
  ```

### Ejecutar desde la terminal (opcional)
```bash
mvn clean compile exec:java
```

### Facturas generadas
Al confirmar una venta, la factura se guarda en PDF en la carpeta `facturas/`, en la raíz
del proyecto (por ejemplo, `facturas/FE-20261103-205605.pdf`). La carpeta se crea
automáticamente la primera vez que se genera una factura.

### Registro de eventos (logs)
El sistema guarda un registro de las acciones importantes y de los errores en el archivo
`erp.log`, en la raíz del proyecto. El archivo se crea solo la primera vez y cada nueva
línea se añade al final. Cada línea tiene la fecha y hora, el tipo (`INFO` o `ERROR`) y
el mensaje:

```
2026-11-04T11:44:03.101615200 | INFO | Lote registrado: L-20261104-114403
2026-11-04T11:48:18.480912200 | ERROR | Error al registrar cliente: Duplicate entry '900111222-5' for key 'clientes.documento'
2026-11-04T11:50:50.154086700 | INFO | Venta registrada: V-20261104-115040, total 385560.0
```

Se registran: proveedores agregados, lotes registrados, mermas, clientes registrados,
ventas, errores al generar el PDF y rutas de entrega creadas, además de los errores de base
de datos de cada una de esas operaciones.

## Estructura del Proyecto
El código sigue una arquitectura **MVC por componentes**: cada módulo del negocio tiene
sus propios paquetes `model`, `view` y `controller`.

```
src/main/java/com/frescuratotal/erp/
├── common/
│   ├── DatabaseConnection.java   // Conexión JDBC a MySQL
│   ├── LogManager.java           // Escribe los eventos y errores en erp.log
│   └── view/MainView.java        // Menú principal
├── inventory/                    // Módulo 1: Inventario y calidad
│   ├── model/      // Product, Category, Lot, Quarantine, Inspection, Waste y sus DAO
│   ├── view/       // ProductView, LotView
│   └── controller/ // ProductController, LotController
├── suppliers/                    // Módulo 2: Proveedores y compras
│   ├── model/      // Supplier, SupplierDao
│   ├── view/       // SupplierView
│   └── controller/ // SupplierController
├── sales/                        // Módulo 3: Ventas y facturación
│   ├── model/      // Customer, Sale, SaleDetail, Invoice y sus DAO
│   ├── view/       // CustomerView, SaleView, InvoicePdf
│   └── controller/ // CustomerController, SaleController
├── logistics/                    // Módulo 4: Logística y entregas
│   ├── model/      // Driver, Vehicle, DeliveryRoute, DeliveryOrder y sus DAO
│   ├── view/       // LogisticsView
│   └── controller/ // LogisticsController
└── Main.java                     // Punto de entrada: crea los controladores y muestra el menú

docs/
├── database.sql            // Script DDL + DML
├── diagrama_er.png         // Diagrama entidad-relación
└── diagrama_clases.png     // Diagrama de clases UML
```

- **Model:** las entidades del negocio (`Lot`, `Sale`...) y los DAO que las leen y guardan en MySQL.
- **View:** todo lo que se muestra o se pide al usuario por consola, más la factura en PDF (`InvoicePdf`).
- **Controller:** la lógica de cada caso de uso (FEFO, promoción, validaciones,
  cambios de estado). Recibe los datos de la vista, usa los DAO y decide qué mostrar.

Diagramas:
- [Diagrama entidad-relación](docs/diagrama_er.png)
- [Diagrama de clases UML](docs/diagrama_clases.png)

## Principios y Patrones de Diseño

### Patrón MVC (Modelo-Vista-Controlador)
Cada módulo separa los datos (`model`), la interacción con el usuario (`view`) y la lógica
(`controller`). Por ejemplo, en una venta, `SaleView` solo pide y muestra datos,
`SaleController` aplica el FEFO, la promoción y el IVA, y `SaleDao` guarda el resultado.
Esta separación permite cambiar la consola por una interfaz gráfica sin tocar la lógica ni
el acceso a datos.

### Patrón DAO (Data Access Object)
Todo el SQL está en clases `...Dao` (`LotDao`, `SaleDao`, `RouteDao`...). Los
controladores nunca escriben SQL: llaman a métodos como `obtenerLotesDisponiblesPorProducto()`
o `registrarVenta()`. Así, si cambia la base de datos o una consulta, el cambio se hace en
un solo lugar. Todos los DAO usan `PreparedStatement` con parámetros `?` para evitar la
inyección SQL.

### Principio de Responsabilidad Única (S de SOLID)
Cada clase tiene una sola razón para cambiar: las entidades solo guardan datos, los DAO
solo acceden a MySQL, las vistas solo leen y escriben en la consola, `InvoicePdf` solo
genera el PDF y `DatabaseConnection` solo crea conexiones. Por ejemplo, si cambia el diseño
de la factura, solo se modifica `InvoicePdf`.

### Inyección de dependencias por constructor (relacionado con la D de SOLID)
Los controladores no crean sus propios DAO ni vistas: los reciben en el constructor
(`new SaleController(new SaleDao(), new SaleView(scanner), ...)`) y `Main` es quien los
arma. Esto deja claras las dependencias de cada controlador y permite reutilizar el mismo
`Scanner` en toda la aplicación.

### Clase utilitaria con método de fábrica (`DatabaseConnection`)
`DatabaseConnection` tiene constructor privado y un método estático `getConnection()` que
centraliza la URL, el usuario y la contraseña. Ninguna otra clase conoce las credenciales,
y cambiarlas solo requiere editar este archivo.

### Transacciones para mantener la integridad
Las operaciones que tocan varias tablas se hacen en una sola transacción
(`setAutoCommit(false)`, `commit()` y `rollback()`):
- `SaleDao.registrarVenta()`: guarda la venta y sus detalles y descuenta el stock de cada lote.
- `RouteDao.crearRuta()`: guarda la ruta, la asignación de vehículo y conductor, y los pedidos.

Si algo falla a mitad, se deshace todo y la base de datos no queda inconsistente (por
ejemplo, una venta registrada sin haber descontado el inventario).

### Registro centralizado de eventos (`LogManager`)
`LogManager` tiene un método estático `registrar(tipo, mensaje)` que usa `FileWriter` en
modo de añadir (`append`) para escribir en `erp.log`. Los controladores solo lo llaman con
una línea, sin saber cómo ni dónde se guarda el log. Si mañana se quisiera guardar el log en
otro archivo o en la base de datos, solo se cambiaría esta clase.

### Expresiones lambda y Stream API
Algunos recorridos de listas se escribieron de forma funcional, con lambdas y streams:

| Dónde | Código | Qué hace |
|---|---|---|
| `LotView.mostrarProductos()` | `productos.forEach(producto -> ...)` | Imprime cada producto |
| `LotView.mostrarProveedores()` | `proveedores.forEach(proveedor -> ...)` | Imprime cada proveedor |
| `SupplierController` (agregar al catálogo) | `productos.stream().anyMatch(...)` | Comprueba si existe un producto con el ID escrito |
| `SaleController.cantidadYaEnPedido()` | `stream().filter(...).mapToDouble(...).sum()` | Suma lo que ya se pidió de un lote para no vender más stock del que hay |

Ejemplo:
```java
return venta.getDetalles().stream()
        .filter(detalle -> detalle.getIdLote() == idLote)
        .mapToDouble(detalle -> detalle.getCantidadVendida())
        .sum();
```
