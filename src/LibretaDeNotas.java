package src;

import javax.xml.validation.Validator;
import java.util.HashMap;
import java.util.RandomAccess;
import java.util.Scanner;
import java.util.ArrayList;

public class LibretaDeNotas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        HashMap<String, HashMap<String, ArrayList<Double>>> Notas = new HashMap<>();


        //Solicitar cantidad de notas y alumnos.
        System.out.println("Ingrese el N° de alumnos: ");
        int cantidadAlumnos = sc.nextInt();

        //Solicitar cantidad de ramos (materias)
        System.out.println("Ingrese la cantidad de materias");
        int cantidadRamos = sc.nextInt();

        //Crear una lista de nombre de ramos
        ArrayList<String> nombresRamos = new ArrayList<>();
        for (int i = 0; i < cantidadRamos; i++) {
            System.out.println("Ingrese el nombre del ramo " + (i + 1) + ": ");
            String nombreRamo = sc.next();
            nombresRamos.add(nombreRamo);
        }

        //Solicitar nombres y notas de los alumnos
        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.println("Ingrese nombre del alumno " + (i + 1) + ": ");
            String nombreAlumno = sc.next();

            // HasMap para almacenar las notas por ramo
            HashMap<String, ArrayList<Double>> notasPorRamo = new HashMap<>();

            // Solicitar las notas por ramo
            for (String ramo : nombresRamos) {
                ArrayList<Double> notasAlumno = new ArrayList<>();
                System.out.println("Ingrese las notas de " + nombreAlumno + " en " + ramo + ":");

                //Solicitar la cantidad de notas por ramos
                System.out.print("¿Cuantas notas desea ingresar para " + ramo + "? ");
                double cantidadNotas = sc.nextDouble();


                // Ingresar las notasfor
                for (int j = 0; j < cantidadNotas; j++) {
                    System.out.println("Ingrese la nota " + (j + 1) + " de " + ramo + ": ");
                    double nota = sc.nextDouble();

                    // Validar que la nota este en un rango valido (1.0 - 7.0)
                    if (nota >= 1.0 && nota <= 7.0) {
                        notasAlumno.add(nota);
                    } else if (nota >= 1.0 && nota <= 7.0) {
                        System.out.println("Nota invalida, Por favor reintentar");
                        i--;// Repetir la entrada de la nota
                    }
                }

                notasPorRamo.put(ramo, notasAlumno);
            }

            Notas.put(nombreAlumno, notasPorRamo);

        }

// Menú de opciones
        int opcion;
        do {
            System.out.println("Menu de Opciones: ");
            System.out.println("1. Mostrar el promedio de Notas por Estudiante y Ramos.");
            System.out.println("2. Mostrar si la Nota Es Aprobatoria o Reprobatoria por Estudiante y ramos.");
            System.out.println("3. Mostrar si la Nota esta por Sobre o por debajo del Promedio del curso por estudiante y ramos");
            System.out.println("0. Salir");
            System.out.println("Seleccione una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    mostrarPromedios(Notas);
                    break;
                case 2:
                    verificarAprobacion(sc, Notas);
                    break;
                case 3:
                    verificarSobrePromedios(sc, Notas);
                    break;
                case 0:
                    System.out.println("Saliendo al menu...");
                    break;
                default:
                    System.out.println("Opcion invalida, reintente");


            }

        } while (opcion != 0);


        sc.close();

    }
// Funcion para mostrar los promedios de notas por estudiante y ramo

    public static void mostrarPromedios(HashMap<String, HashMap<String, ArrayList<Double>>> Notas) {
        for (String nombreAlumno : Notas.keySet()) {
            System.out.println("Promedio de " + nombreAlumno + ":");
            HashMap<String, ArrayList<Double>> notasPorRamo = Notas.get(nombreAlumno);

            for (String ramo : notasPorRamo.keySet()) {
                ArrayList<Double> notas = notasPorRamo.get(ramo);
                double promedio = calcularPromedio(notas);
                System.out.println("Ramo " + ramo + ", Promedio: " + String.format("%.2f", promedio));

            }

        }
    }

    //Funcion para calcular el promedio de una lista de notas
    public static double calcularPromedio(ArrayList<Double> notas) {
        double suma = 0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.size();
    }

    // Funcion para verificar si una nota es aprobatoria o reprobatoria
    public static void verificarAprobacion(Scanner scanner, HashMap<String, HashMap<String, ArrayList<Double>>> Notas) {
        System.out.println("Ingrese el nombre del Alumno: ");
        String nombreAlumno = scanner.next();

        if (Notas.containsKey(nombreAlumno)) {
            System.out.println("ingrese Nombre del Ramo: ");
            String ramo = scanner.next();

            HashMap<String, ArrayList<Double>> notasPorRamo = Notas.get(nombreAlumno);
            if (notasPorRamo.containsKey(ramo)) {
                System.out.println("Ingrese la nota a verificar: ");
                double nota = scanner.nextDouble();

                // Validar que la nota este en el rango correcto
                if (nota >= 1.0 && nota <= 7.0) {
                    if (nota >= 4.0) { // Nota de aprobacion en Chile es 4.0
                        System.out.println("La nota es Aprobatoria.");
                    } else {
                        System.out.println("La nota es Reprobatoria.");
                    }
                } else {
                    System.out.println("La nota es invalida.");
                }
            } else {
                System.out.println("Ramo no encontrado.");
            }
        } else {
            System.out.println("El nombre del Alumno no existe");
        }
    }


    //Funcion para verificar si un nota esta por sobre o por debajo del promedio del curso
    public static void verificarSobrePromedios(Scanner scanner, HashMap<String, HashMap<String, ArrayList<Double>>> Notas) {
        System.out.println("Ingrese el nombre del alumno: ");
        String nombreAlumno = scanner.next();

        if (Notas.containsKey(nombreAlumno)) {
            System.out.println("ingrese Nombre del Ramo: ");
            String ramo = scanner.next();

            HashMap<String, ArrayList<Double>> notasPorRamo = Notas.get(nombreAlumno);
            if (notasPorRamo.containsKey(ramo)) {
                ArrayList<Double> notas = notasPorRamo.get(ramo);
                double promedioCurso = calcularPromedioCurso(Notas, ramo);

                System.out.println("Ingrese la nota a verificar: ");
                double nota = scanner.nextDouble();


                // Validar que la nota esté em el rango correcto
                if (nota >= 1.0 && nota <= 7.0) {
                    if (nota > promedioCurso) {
                        System.out.println("La nota esta por Sobre el promedio del curso. ");
                    } else if (nota < promedioCurso) {
                        System.out.println("La nota esta por Debajo del promedio del curso. ");
                    } else {
                        System.out.println("La nota es Igual al promedio del curso.");
                    }
                } else {
                    System.out.println("La nota es invalida.");
                }
            } else {
                System.out.println("Ramo no existe");
            }
        } else {
            System.out.println("El Alumno no existe");
        }
    }

    //funcion para calcular el promedio del curso por ramo
    public static double calcularPromedioCurso(HashMap<String, HashMap<String, ArrayList<Double>>> Notas, String ramo) {
        double suma = 0;
        int cantidadNotas = 0;

        for (HashMap<String, ArrayList<Double>> notasPorRamo : Notas.values()) {
            if (notasPorRamo.containsKey(ramo)) {
                ArrayList<Double> notas = notasPorRamo.get(ramo);
                for (double nota : notas) {
                    suma += nota;
                    cantidadNotas++;
                }
            }
        }
        return suma / cantidadNotas;

    }

    }
