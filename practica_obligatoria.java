import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

void main() {
    double dineroTickets = 0.0;
    int ticketsGenerados = 0;
    double dineroMultas = 0.0;
    int multasAnuladas = 0;

    final String adminUser = "admin";
    final String adminPass = "1234";

    boolean salir = false;

    IO.println("""
            ==================================================
               SISTEMA DE GESTIÓN ZONA AZUL - AYUNTAMIENTO DE MARTOS
            ==================================================
            """);

    while (!salir) {
        String opcion = "";
        boolean opcionValida = false;

        do {
            opcion = IO.readln("""
                    
                    --- MENÚ PRINCIPAL ---
                    1. Obtener un ticket
                    2. Anular una multa
                    3. Consultar fecha y hora actual, tarifas y horarios
                    4. Menú de administrador
                    Elija una opción (1-4):\s""").trim();

            if (opcion.isEmpty()) {
                IO.println("El campo no puede estar vacío. Inténtelo de nuevo.");
            } else if (opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4")) {
                opcionValida = true;
            } else {
                IO.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (!opcionValida);

        switch (opcion) {
            case "1" -> {
                LocalDateTime ahora = LocalDateTime.now();
                boolean modoValido = false;
                do {
                    String respuestaModo = IO.readln("¿Desea introducir la hora de emisión manualmente? (true/false): ").trim();
                    if (respuestaModo.isEmpty()) {
                        IO.println("El campo no puede estar vacío. Inténtelo de nuevo.");
                    } else if (respuestaModo.equals("true") || respuestaModo.equals("false")) {
                        boolean manual = Boolean.parseBoolean(respuestaModo);
                        if (manual) {
                            int anio;
                            do {
                                String entradaAnio = IO.readln("Año (ej. 2026): ").trim();
                                if (entradaAnio.isEmpty()) {
                                    IO.println("El campo no puede estar vacío.");
                                    anio = -1;
                                } else {
                                    anio = Integer.parseInt(entradaAnio);
                                    if (anio < 0) IO.println("No puedes poner un año negativo");
                                }
                            } while (!(anio > 0));

                            int mes;
                            do {
                                String entradaMes = IO.readln("Mes (1-12): ").trim();
                                if (entradaMes.isEmpty()) {
                                    IO.println("El campo no puede estar vacío.");
                                    mes = -1;
                                } else {
                                    mes = Integer.parseInt(entradaMes);
                                    if (mes > 12 || mes < 1) IO.println("Esta fuera de rango debe de ser entre 1 y 12");
                                }
                            } while (mes < 1 || mes > 12);

                            int dia;
                            do {
                                String entradaDia = IO.readln("Día (1-31): ").trim();
                                if (entradaDia.isEmpty()) {
                                    IO.println("El campo no puede estar vacío.");
                                    dia = -1;
                                } else {
                                    dia = Integer.parseInt(entradaDia);
                                    if (dia > 31 || dia < 1) IO.println("Esta fuera de rango debe ser entre 1 y 31");
                                }
                            } while (dia < 1 || dia > 31);

                            int hora;
                            do {
                                String entradaHora = IO.readln("Hora (0-23): ").trim();
                                if (entradaHora.isEmpty()) {
                                    IO.println("El campo no puede estar vacío.");
                                    hora = -1;
                                } else {
                                    hora = Integer.parseInt(entradaHora);
                                    if (hora > 23 || hora < 0) IO.println("Fuera de rango");
                                }
                            } while (hora < 0 || hora > 23);

                            int minuto;
                            do {
                                String entradaMinuto = IO.readln("Minutos (0-59): ").trim();
                                if (entradaMinuto.isEmpty()) {
                                    IO.println("El campo no puede estar vacío.");
                                    minuto = -1;
                                } else {
                                    minuto = Integer.parseInt(entradaMinuto);
                                    if (minuto < 0 || minuto > 59) IO.println("Valor fuera de rango o no valido");
                                }
                            } while (minuto < 0 || minuto > 59);

                            ahora = LocalDateTime.of(anio, mes, dia, hora, minuto, 0);
                        }
                        modoValido = true;
                    } else {
                        IO.println("Debe escribir exactamente 'true' o 'false'.");
                    }
                } while (!modoValido);

                boolean esResidente = false;
                boolean residenteValido = false;
                do {
                    String respResidente = IO.readln("¿Es usted residente? (true/false): ").trim();
                    if (respResidente.isEmpty()) {
                        IO.println("El campo no puede estar vacío. Inténtelo de nuevo.");
                    } else if (respResidente.equals("true") || respResidente.equals("false")) {
                        esResidente = Boolean.parseBoolean(respResidente);
                        residenteValido = true;
                    } else {
                        IO.println("Debe escribir exactamente 'true' o 'false'.");
                    }
                } while (!residenteValido);

                double dinero = 0.0;
                boolean dineroValido = false;
                IO.println("""
                        --- TARIFAS ---
              Duración  | No Residentes | Residentes
              00:15:00  |    0,25 €     |   0,10 €
              00:29:50  |    0,45 €     |   0,20 €
              00:59:31  |    0,85 €     |   0,40 €
              01:29:12  |    1,25 €     |   0,60 €
              02:00:00  |    1,70 €     |   0,80 €
                            """);
                do {
                    String entradaDinero = IO.readln("Dinero introducido (€): ").trim().replace(",", ".");
                    if (entradaDinero.isEmpty()) {
                        IO.println("El campo no puede estar vacío.");
                    } else {
                        dinero = Double.parseDouble(entradaDinero);
                        if (dinero > 0) {
                            dineroValido = true;
                        } else {
                            IO.println("La cantidad debe ser mayor que 0.");
                        }
                    }
                } while (!dineroValido);

                double precio1 = esResidente ? 0.10 : 0.25;
                double precio2 = esResidente ? 0.20 : 0.45;
                double precio3 = esResidente ? 0.40 : 0.85;
                double precio4 = esResidente ? 0.60 : 1.25;
                double precio5 = esResidente ? 0.80 : 1.70;

                int minutosComprados = 0;
                double importePagado = 0.0;

                if (dinero >= precio5) {
                    minutosComprados = 120;
                    importePagado = precio5;
                } else if (dinero >= precio4) {
                    minutosComprados = 90;
                    importePagado = precio4;
                } else if (dinero >= precio3) {
                    minutosComprados = 60;
                    importePagado = precio3;
                } else if (dinero >= precio2) {
                    minutosComprados = 30;
                    importePagado = precio2;
                } else if (dinero >= precio1) {
                    minutosComprados = 15;
                    importePagado = precio1;
                } else {
                    IO.println("Cantidad insuficiente para el ticket mínimo.");
                }

                if (minutosComprados > 0) {
                    LocalDateTime finEstacionamiento = ahora;
                    int minutosRestantes = minutosComprados;

                    while (minutosRestantes > 0) {
                        DayOfWeek diaSemana = finEstacionamiento.getDayOfWeek();
                        int h = finEstacionamiento.getHour();
                        int m = finEstacionamiento.getMinute();
                        int totalMin = h * 60 + m;

                        boolean activo = false;
                        if (diaSemana != DayOfWeek.SUNDAY) {
                            if (diaSemana == DayOfWeek.SATURDAY) {
                                if (totalMin >= (9 * 60) && totalMin < (14 * 60)) {
                                    activo = true;
                                }
                            } else {
                                boolean manana = (totalMin >= (9 * 60) && totalMin < (14 * 60));
                                boolean tarde = (totalMin >= (17 * 60) && totalMin < (21 * 60));
                                if (manana || tarde) {
                                    activo = true;
                                }
                            }
                        }

                        if (activo) {
                            minutosRestantes--;
                        }
                        if (minutosRestantes > 0) {
                            finEstacionamiento = finEstacionamiento.plusMinutes(1);
                        }
                    }

                    int numeroTicket = (minutosComprados * 13) + 7;
                    dineroTickets += importePagado;
                    ticketsGenerados++;

                    IO.println("""
                            --------------------------------------------------
                                        AYUNTAMIENTO DE MARTOS                
                                             ZONA AZUL                        
                            --------------------------------------------------
                            Fecha y hora de emisión: %s
                            Residente: %b
                            Minutos comprados: %d
                            Importe pagado: %.2f €
                            Fin estacionamiento: %s
                            Día-mes vencimiento: %s
                            Nº de Ticket: %d
                            --------------------------------------------------
                            """.formatted(
                            ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                            esResidente,
                            minutosComprados,
                            importePagado,
                            finEstacionamiento.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                            finEstacionamiento.format(DateTimeFormatter.ofPattern("dd/MM")),
                            numeroTicket
                    ));
                }
            }
            case "2" -> {
                int opcionMulta = 0;
                boolean opcionValidaMulta = false;

                do {
                    String entradaOpcionMulta = IO.readln("""
                            2.1. Por exceder el tiempo permitido (3,00 €)
                            2.2. Por carecer de ticket (6,00 €)
                            Elija tipo de anulación (1 o 2):\s""").trim();

                    if (entradaOpcionMulta.isEmpty()) {
                        IO.println("El campo no puede estar vacío. Inténtelo de nuevo.");
                    } else if (entradaOpcionMulta.equals("1") || entradaOpcionMulta.equals("2")) {
                        opcionMulta = Integer.parseInt(entradaOpcionMulta);
                        opcionValidaMulta = true;
                    } else {
                        IO.println("Opción no válida. Introduzca 1 o 2.");
                    }
                } while (!opcionValidaMulta);

                if (opcionMulta == 1) {
                    int numeroTicket = 0;
                    boolean ticketValido = false;

                    do {
                        String entradaTicket = IO.readln("Introduce el número del ticket asociado a la multa: ").trim();
                        if (entradaTicket.isEmpty()) {
                            IO.println("El campo no puede estar vacío.");
                        } else {
                            numeroTicket = Integer.parseInt(entradaTicket);
                            ticketValido = true;
                        }
                    } while (!ticketValido);

                    int verificacion = (numeroTicket - 7) / 13;

                    if (verificacion == 15 || verificacion == 30 || verificacion == 60 || verificacion == 90 || verificacion == 120) {
                        int refAnulacion = (numeroTicket * 3) + 99;
                        dineroMultas += 3.00;
                        multasAnuladas++;

                        IO.println("""
                                --------------------------------------------------
                                          TICKET DE ANULACIÓN DE MULTA            
                                             AYUNTAMIENTO DE MARTOS               
                                --------------------------------------------------
                                Motivo: Exceder el tiempo permitido
                                Importe abonado: 3,00 €
                                Referencia anulación: %d
                                --------------------------------------------------
                                """.formatted(refAnulacion));
                    } else {
                        IO.println("El número de ticket no es válido. No se puede anular.");
                    }

                } else if (opcionMulta == 2) {
                    int refAnulacion = 54321;
                    dineroMultas += 6.00;
                    multasAnuladas++;

                    IO.println("""
                            --------------------------------------------------
                                      TICKET DE ANULACIÓN DE MULTA            
                                         AYUNTAMIENTO DE MARTOS               
                            --------------------------------------------------
                            Motivo: Carecer de ticket
                            Importe abonado: 6,00 €
                            Referencia anulación: %d
                            --------------------------------------------------
                            """.formatted(refAnulacion));
                }
            }
            case "3" -> {
                LocalDateTime ahoraInfo = LocalDateTime.now();
                IO.println("""
                        --- TARIFAS ---
              Duración  | No Residentes | Residentes
              00:15:00  |    0,25 €     |   0,10 €
              00:29:50  |    0,45 €     |   0,20 €
              00:59:31  |    0,85 €     |   0,40 €
              01:29:12  |    1,25 €     |   0,60 €
              02:00:00  |    1,70 €     |   0,80 €
                            """);
                IO.println("""
                        Fecha y hora actual: %s
                        Horarios: L-V 09:00-14:00 y 17:00-21:00. Sábados 09:00-14:00. 
                        Domingo cerrado.
                        """.formatted(ahoraInfo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
            }
            case "4" -> {
                String usuarioAdmin = "";
                do {
                    usuarioAdmin = IO.readln("Usuario administrador: ").trim();
                    if (usuarioAdmin.isEmpty()) {
                        IO.println("El campo no puede estar vacío. Inténtelo de nuevo.");
                    }
                } while (usuarioAdmin.isEmpty());

                String claveAdmin = "";
                do {
                    claveAdmin = IO.readln("Contraseña: ").trim();
                    if (claveAdmin.isEmpty()) {
                        IO.println("El campo no puede estar vacío. Inténtelo de nuevo.");
                    }
                } while (claveAdmin.isEmpty());

                if (usuarioAdmin.equals(adminUser) && claveAdmin.equals(adminPass)) {
                    boolean volverAdmin = false;
                    while (!volverAdmin) {
                        String opAdmin = "";
                        boolean opAdminValida = false;
                        do {
                            opAdmin = IO.readln("""
                                    
                                    --- MENÚ DE ADMINISTRADOR ---
                                    4.1. Dinero ingresado en tickets y tickets generados
                                    4.2. Dinero ingresado por multas y multas anuladas
                                    4.3. Apagar el software
                                    4.4. Volver al menú principal
                                    Elija una opción:\s""").trim();

                            if (opAdmin.isEmpty()) {
                                IO.println("El campo no puede estar vacío. Inténtelo de nuevo.");
                            } else if (opAdmin.equals("4.1") || opAdmin.equals("1") ||
                                    opAdmin.equals("4.2") || opAdmin.equals("2") ||
                                    opAdmin.equals("4.3") || opAdmin.equals("3") ||
                                    opAdmin.equals("4.4") || opAdmin.equals("4")) {
                                opAdminValida = true;
                            } else {
                                IO.println("Opción de administrador no válida.");
                            }
                        } while (!opAdminValida);

                        switch (opAdmin) {
                            case "4.1", "1" -> {
                                IO.println("""
                                        
                                        --- ESTADÍSTICAS DE TICKETS ---
                                         Tickets generados:   %d
                                         Dinero ingresado:    %.2f €
                                        """.formatted(ticketsGenerados, dineroTickets));
                            }
                            case "4.2", "2" -> {
                                IO.println("""
                                        
                                        --- ESTADÍSTICAS DE MULTAS ---
                                         Multas anuladas:     %d
                                         Dinero ingresado:    %.2f €
                                        """.formatted(multasAnuladas, dineroMultas));
                            }
                            case "4.3", "3" -> {
                                IO.println("Apagando el software del parquímetro por orden del administrador. ¡Hasta pronto!");
                                salir = true;
                                volverAdmin = true;
                            }
                            case "4.4", "4" -> {
                                volverAdmin = true;
                            }
                        }
                    }
                } else {
                    IO.println("Credenciales incorrectas. Acceso denegado.");
                }
            }
        }
    }
}