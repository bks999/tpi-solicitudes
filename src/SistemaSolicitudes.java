import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

// Clase principal del sistema
public class SistemaSolicitudes {

    public static ArrayList<Solicitud> lista = new ArrayList<>();
    public static ArrayList<Estudiante> lista2 = new ArrayList<>();
    public static ArrayList<Responsable> resp = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int ultimoId = 0;
    static boolean flag = false;

    public static void iniciar() {
        cargarDatos();
        int op = -1;
        while (op != 0) {
            System.out.println("");
            System.out.println("===== SISTEMA DE SOLICITUDES ACADEMICAS =====");
            System.out.println("1. Registrar solicitud");
            System.out.println("2. Cambiar estado de solicitud");
            System.out.println("3. Asignar responsable");
            System.out.println("4. Buscar / filtrar solicitudes");
            System.out.println("5. Generar reporte");
            System.out.println("6. Listar todas las solicitudes");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                op = -1;
            }
            if (op == 1) {
                registrarSolicitud();
                guardarDatos();
            } else if (op == 2) {
                cambiarEstado();
                guardarDatos();
            } else if (op == 3) {
                asignarResponsable();
                guardarDatos();
            } else if (op == 4) {
                buscar();
            } else if (op == 5) {
                reporte();
            } else if (op == 6) {
                for (int i = 0; i < lista.size(); i++) {
                    mostrar(lista.get(i));
                }
            } else if (op == 0) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opcion invalida");
            }
        }
        guardarDatos();
    }

    public static void registrarSolicitud() {
        System.out.print("Legajo del estudiante: ");
        String leg = sc.nextLine().trim();
        if (leg.equals("") || leg.length() < 4) {
            System.out.println("Error: legajo invalido");
            return;
        }
        boolean existe = false;
        for (int i = 0; i < lista2.size(); i++) {
            if (lista2.get(i).getLegajo().equals(leg)) {
                existe = true;
            }
        }
        if (!existe) {
            System.out.println("El estudiante no existe, se va a registrar.");
            System.out.print("Nombre: ");
            String n = sc.nextLine().trim();
            System.out.print("Apellido: ");
            String a = sc.nextLine().trim();
            System.out.print("Email: ");
            String e = sc.nextLine().trim();
            System.out.print("Carrera: ");
            String c = sc.nextLine().trim();
            if (n.equals("") || a.equals("")) {
                System.out.println("Error: nombre y apellido son obligatorios");
                return;
            }
            if (!e.contains("@") || !e.contains(".")) {
                System.out.println("Error: email invalido");
                return;
            }
            Estudiante est = new Estudiante(leg, n, a, e, c);
            lista2.add(est);
        }

        String t = seleccionarTipoSolicitud();
        if (t == null) {
            return;
        }

        int p = seleccionarPrioridad();
        if (p == 0) {
            return;
        }

        System.out.print("Descripcion: ");
        String d = sc.nextLine().trim();
        d = d.replace(";", ",");
        if (d.length() < 10) {
            System.out.println("Error: la descripcion debe tener al menos 10 caracteres");
            return;
        }

        // si es baja o examen y dice urgente se sube la prioridad
        if ((t.equals("BAJA_MATERIA") || t.equals("EXAMEN_ESPECIAL")) && d.toLowerCase().contains("urgente")) {
            p = 3;
            System.out.println("Aviso: la prioridad se elevo a ALTA por tratarse de un tramite urgente.");
        }

        ultimoId++;

        DatosSolicitud datos = new DatosSolicitud(
            ultimoId,
            leg,
            t,
            p,
            "PENDIENTE",
            d,
            LocalDate.now().toString(),
            0,
            ""
        );

        Solicitud s = Solicitud.crearSolicitud(datos);        lista.add(s);
            System.out.println("Solicitud registrada con ID " + s.id);
    }

    private static int seleccionarPrioridad() {
        System.out.print("Prioridad (1=baja, 2=media, 3=alta): ");
        int p = 0;

        try {
            p = Integer.parseInt(sc.nextLine());
        } catch (Exception ex) {
            p = 0;
        }

        if (p < 1 || p > 3) {
            System.out.println("Error: prioridad invalida");
            return 0;
        }

        return p;
    }

    private static String seleccionarTipoSolicitud() {
        System.out.println("Tipo de solicitud:");
        System.out.println("1. Certificado");
        System.out.println("2. Equivalencia");
        System.out.println("3. Baja de materia");
        System.out.println("4. Mesa de examen especial");
        System.out.println("5. Otro");
        System.out.print("Opcion: ");

    int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            opcion = 0;
        }

        switch (opcion) {
            case 1: return "CERTIFICADO";
            case 2: return "EQUIVALENCIA";
            case 3: return "BAJA_MATERIA";
            case 4: return "EXAMEN_ESPECIAL";
            case 5: return "OTRO";
            default:
                System.out.println("Error: tipo invalido");
                return null;
        }
    }

    public static void cambiarEstado() {
        System.out.print("ID de la solicitud: ");
        int id = 0;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            id = 0;
        }
        Solicitud s = null;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).id == id) {
                s = lista.get(i);
            }
        }
        if (s == null) {
            System.out.println("No existe la solicitud");
            return;
        }
        System.out.println("Estado actual: " + s.estado);
        System.out.print("Nuevo estado (PENDIENTE, EN_REVISION, APROBADA, RECHAZADA, CERRADA): ");
        String nuevo = aux(sc.nextLine());
        boolean ok = false;
        if (s.estado.equals("PENDIENTE")) {
            if (nuevo.equals("EN_REVISION")) {
                if (s.idResponsable == 0) {
                    System.out.println("Error: no se puede pasar a revision sin responsable asignado");
                    return;
                }
                ok = true;
            } else if (nuevo.equals("RECHAZADA")) {
                ok = true;
            }
        } else if (s.estado.equals("EN_REVISION")) {
            if (nuevo.equals("APROBADA") || nuevo.equals("RECHAZADA")) {
                ok = true;
            }
        } else if (s.estado.equals("APROBADA")) {
            if (nuevo.equals("CERRADA")) {
                ok = true;
            }
        } else if (s.estado.equals("RECHAZADA")) {
            if (nuevo.equals("CERRADA")) {
                ok = true;
            }
        } else if (s.estado.equals("CERRADA")) {
            System.out.println("La solicitud ya esta cerrada, no se puede modificar");
            return;
        }
        if (ok) {
            String viejo = s.estado;
            s.estado = nuevo;
            s.observaciones = s.observaciones + "[" + LocalDate.now() + "] " + viejo + " -> " + nuevo + " | ";
            System.out.println("Estado actualizado correctamente");
        } else {
            System.out.println("Error: transicion no permitida de " + s.estado + " a " + nuevo);
        }
    }

    // normaliza el texto
    public static String aux(String x) {
        return x.trim().toUpperCase().replace(" ", "_");
    }

    public static void asignarResponsable() {
        System.out.print("ID de la solicitud: ");
        int id = 0;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            id = 0;
        }
        Solicitud sol = null;
        for (Solicitud s : lista) {
            if (s.id == id) sol = s;
        }
        if (sol == null) {
            System.out.println("No existe la solicitud");
            return;
        }
        if (sol.estado.equals("CERRADA") || sol.estado.equals("RECHAZADA")) {
            System.out.println("Error: no se puede asignar responsable a una solicitud " + sol.estado);
            return;
        }
        System.out.println("Responsables disponibles:");
        for (Responsable r : resp) {
            System.out.println(r.id + " - " + r.nombre + " (" + r.area + ")");
        }
        System.out.print("ID del responsable: ");
        int idR = 0;
        try {
            idR = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            idR = 0;
        }
        Responsable r = null;
        for (Responsable rr : resp) {
            if (rr.id == idR) r = rr;
        }
        if (r == null) {
            System.out.println("No existe el responsable");
            return;
        }

        // controlar que el area corresponda al tipo
        String areaEsperada = "";
        if (sol.tipo.equals("CERTIFICADO")) areaEsperada = "BEDELIA";
        else if (sol.tipo.equals("EQUIVALENCIA")) areaEsperada = "SECRETARIA_ACADEMICA";
        else if (sol.tipo.equals("BAJA_MATERIA")) areaEsperada = "DEPARTAMENTO_ALUMNOS";
        else if (sol.tipo.equals("EXAMEN_ESPECIAL")) areaEsperada = "BEDELIA";
        if (!areaEsperada.equals("") && !r.area.equals(areaEsperada)) {
            System.out.print("Atencion: el tipo " + sol.tipo + " corresponde al area " + areaEsperada + ". Asignar igual? (s/n): ");
            String resp2 = sc.nextLine();
            if (!resp2.equalsIgnoreCase("s")) {
                System.out.println("Asignacion cancelada");
                return;
            }
        }

        // un responsable no puede tener mas de 5 solicitudes activas
        int cant = 0;
        for (Solicitud s : lista) {
            if (s.idResponsable == r.id && !s.estado.equals("CERRADA") && !s.estado.equals("RECHAZADA")) {
                cant++;
            }
        }
        if (cant >= 5) {
            System.out.println("Error: el responsable ya tiene 5 solicitudes activas");
            return;
        }
        sol.idResponsable = r.id;
        System.out.println("Responsable " + r.nombre + " asignado a la solicitud " + sol.id);
    }

    public static void buscar() {
        System.out.println("Buscar por: 1. Estado  2. Tipo  3. Prioridad  4. Legajo  5. Responsable");
        System.out.print("Opcion: ");
        String o = sc.nextLine().trim();
        if (o.equals("1")) {
            System.out.print("Estado: ");
            String e = aux(sc.nextLine());
            int c = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).estado.equals(e)) {
                    mostrar(lista.get(i));
                    c++;
                }
            }
            System.out.println(c + " resultado(s)");
        } else if (o.equals("2")) {
            System.out.print("Tipo: ");
            String t = aux(sc.nextLine());
            int c = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).tipo.equals(t)) {
                    mostrar(lista.get(i));
                    c++;
                }
            }
            System.out.println(c + " resultado(s)");
        } else if (o.equals("3")) {
            System.out.print("Prioridad (1-3): ");
            int p = Integer.parseInt(sc.nextLine());
            int c = 0;
            for (Solicitud s : lista) {
                if (s.prioridad == p) {
                    mostrar(s);
                    c++;
                }
            }
            System.out.println(c + " resultado(s)");
        } else if (o.equals("4")) {
            System.out.print("Legajo: ");
            String l = sc.nextLine().trim();
            int c = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).legajo.equals(l)) {
                    mostrar(lista.get(i));
                    c++;
                }
            }
            System.out.println(c + " resultado(s)");
        } else if (o.equals("5")) {
            System.out.print("ID responsable (0 = sin asignar): ");
            int r = 0;
            try {
                r = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
            }
            int c = 0;
            for (Solicitud s : lista) {
                if (s.idResponsable == r) {
                    mostrar(s);
                    c++;
                }
            }
            System.out.println(c + " resultado(s)");
        } else {
            System.out.println("Opcion invalida");
        }
    }

    public static void mostrar(Solicitud s) {
        String nom = "";
        for (Estudiante e : lista2) {
            if (e.getLegajo().equals(s.legajo)) nom = e.getApellido() + ", " + e.getNombre();
        }
        String nomR = "Sin asignar";
        for (Responsable r : resp) {
            if (r.id == s.idResponsable) nomR = r.nombre;
        }
        String pr = "";
        if (s.prioridad == 1) pr = "BAJA";
        else if (s.prioridad == 2) pr = "MEDIA";
        else if (s.prioridad == 3) pr = "ALTA";
        System.out.println("#" + s.id + " | " + s.fecha + " | " + s.tipo + " | Prioridad: " + pr + " | Estado: " + s.estado
                + " | Estudiante: " + nom + " (" + s.legajo + ") | Responsable: " + nomR);
        System.out.println("    " + s.descripcion);
    }

    public static void reporte() {
        int cantPend = 0, cantRev = 0, cantAprob = 0, cantRech = 0, cantCerr = 0;
        int cantCert = 0, cantEquiv = 0, cantBaja = 0, cantExam = 0, cantOtro = 0;
        int sumaPrioridad = 0;
        int urgentes = 0;
        int sinAsignar = 0;
        for (int i = 0; i < lista.size(); i++) {
            Solicitud s = lista.get(i);
            if (s.estado.equals("PENDIENTE")) cantPend++;
            if (s.estado.equals("EN_REVISION")) cantRev++;
            if (s.estado.equals("APROBADA")) cantAprob++;
            if (s.estado.equals("RECHAZADA")) cantRech++;
            if (s.estado.equals("CERRADA")) cantCerr++;
            if (s.tipo.equals("CERTIFICADO")) cantCert++;
            if (s.tipo.equals("EQUIVALENCIA")) cantEquiv++;
            if (s.tipo.equals("BAJA_MATERIA")) cantBaja++;
            if (s.tipo.equals("EXAMEN_ESPECIAL")) cantExam++;
            if (s.tipo.equals("OTRO")) cantOtro++;
            sumaPrioridad = sumaPrioridad + s.prioridad;
            if (s.prioridad == 3 && !s.estado.equals("CERRADA") && !s.estado.equals("RECHAZADA")) urgentes++;
            if (s.idResponsable == 0) sinAsignar++;
        }
        double promedio = sumaPrioridad / lista.size();

        String txt = "";
        txt += "========== REPORTE DE SOLICITUDES ==========\n";
        txt += "Fecha: " + LocalDate.now() + "\n";
        txt += "Total de solicitudes: " + lista.size() + "\n\n";
        txt += "-- Por estado --\n";
        txt += "Pendientes: " + cantPend + "\n";
        txt += "En revision: " + cantRev + "\n";
        txt += "Aprobadas: " + cantAprob + "\n";
        txt += "Rechazadas: " + cantRech + "\n";
        txt += "Cerradas: " + cantCerr + "\n";
        txt += "\n-- Por tipo --\n";
        txt += "Certificados: " + cantCert + "\n";
        txt += "Equivalencias: " + cantEquiv + "\n";
        txt += "Bajas de materia: " + cantBaja + "\n";
        txt += "Examenes especiales: " + cantExam + "\n";
        txt += "Otros: " + cantOtro + "\n";
        txt += "\n-- Por responsable --\n";
        for (Responsable r : resp) {
            int c = 0;
            for (Solicitud s : lista) {
                if (s.idResponsable == r.id) c++;
            }
            txt += r.nombre + " (" + r.area + "): " + c + "\n";
        }
        txt += "Sin asignar: " + sinAsignar + "\n";
        txt += "\nPrioridad promedio: " + promedio + "\n";
        txt += "Solicitudes de prioridad alta abiertas: " + urgentes + "\n";
        System.out.println(txt);
        try {
            FileWriter fw = new FileWriter("data/reporte.txt");
            fw.write(txt);
            fw.close();
            System.out.println("Reporte guardado en data/reporte.txt");
        } catch (Exception e) {
            System.out.println("No se pudo guardar el reporte");
        }
    }

    public static void cargarDatos() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/estudiantes.csv"));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().equals("")) continue;
                String[] p = linea.split(";");
                lista2.add(new Estudiante(p[0], p[1], p[2], p[3], p[4]));
            }
            br.close();
        } catch (Exception e) {
            // si no existe el archivo no pasa nada
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/responsables.csv"));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().equals("")) continue;
                String[] p = linea.split(";");
                resp.add(new Responsable(Integer.parseInt(p[0]), p[1], p[2], p[3]));
            }
            br.close();
        } catch (Exception e) {
        }
        if (resp.size() == 0) {
            resp.add(new Responsable(1, "Laura Gomez", "BEDELIA", "lgomez@facultad.edu.ar"));
            resp.add(new Responsable(2, "Diego Sosa", "SECRETARIA_ACADEMICA", "dsosa@facultad.edu.ar"));
            resp.add(new Responsable(3, "Paula Herrera", "DEPARTAMENTO_ALUMNOS", "pherrera@facultad.edu.ar"));
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/solicitudes.csv"));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().equals("")) continue;
                String[] p = linea.split(";", -1);
                Solicitud s = new Solicitud(Integer.parseInt(p[0]), p[1], p[2], Integer.parseInt(p[3]), p[4],
                        p[5], p[6], Integer.parseInt(p[7]), p[8]);
                lista.add(s);
                if (s.id > ultimoId) ultimoId = s.id;
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error al cargar solicitudes");
        }
    }

    public static void guardarDatos() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("data/estudiantes.csv"));
            for (Estudiante e : lista2) {
                pw.println(e.getLegajo() + ";" + e.getNombre() + ";" + e.getApellido() + ";" + e.getEmail() + ";" + e.getCarrera());
            }
            pw.close();
            pw = new PrintWriter(new FileWriter("data/responsables.csv"));
            for (Responsable r : resp) {
                pw.println(r.id + ";" + r.nombre + ";" + r.area + ";" + r.email);
            }
            pw.close();
            pw = new PrintWriter(new FileWriter("data/solicitudes.csv"));
            for (Solicitud s : lista) {
                pw.println(s.id + ";" + s.legajo + ";" + s.tipo + ";" + s.prioridad + ";" + s.estado + ";"
                        + s.descripcion + ";" + s.fecha + ";" + s.idResponsable + ";" + s.observaciones);
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public static void eliminarSolicitud(int id) {
    //     for (int i = 0; i < lista.size(); i++) {
    //         if (lista.get(i).id == id) lista.remove(i);
    //     }
    // }

    public static int calcularDiasAbierta(Solicitud s) {
        // TODO terminar
        return 0;
    }
}
