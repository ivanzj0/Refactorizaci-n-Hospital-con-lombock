package main.java.org.jcr.entidades;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"paciente", "medico"}) // evito ciclos
@ToString(exclude = {"paciente", "medico"})
public class Cita implements Serializable {

    @NonNull
    private final Paciente paciente;

    @NonNull
    private final Medico medico;

    @NonNull
    private final Sala sala;

    @NonNull
    private final LocalDateTime fechaHora;

    @NonNull
    private final BigDecimal costo;

    @Setter
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    @Setter
    private String observaciones = "";

    public String toCsvString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                paciente.getDni(),
                medico.getDni(),
                sala.getNumero(),
                fechaHora.toString(),
                costo.toString(),
                estado.name(),
                observaciones.replaceAll(",", ";"));
    }

    public static Cita fromCsvString(String csvString,
                                     Map<String, Paciente> pacientes,
                                     Map<String, Medico> medicos,
                                     Map<String, Sala> salas) throws CitaException {
        String[] values = csvString.split(",");
        if (values.length != 7) {
            throw new CitaException("Formato de CSV inválido para Cita: " + csvString);
        }

        String dniPaciente = values[0];
        String dniMedico = values[1];
        String numeroSala = values[2];
        LocalDateTime fechaHora = LocalDateTime.parse(values[3]);
        BigDecimal costo = new BigDecimal(values[4]);
        EstadoCita estado = EstadoCita.valueOf(values[5]);
        String observaciones = values[6].replaceAll(";", ",");

        Paciente paciente = pacientes.get(dniPaciente);
        Medico medico = medicos.get(dniMedico);
        Sala sala = salas.get(numeroSala);

        if (paciente == null) throw new CitaException("Paciente no encontrado: " + dniPaciente);
        if (medico == null) throw new CitaException("Médico no encontrado: " + dniMedico);
        if (sala == null) throw new CitaException("Sala no encontrada: " + numeroSala);

        Cita cita = new Cita(paciente, medico, sala, fechaHora, costo);
        cita.setEstado(estado);
        cita.setObservaciones(observaciones);

        return cita;
    }

    @Override
    public String toString() {
        return String.format("Cita{paciente=%s, medico=%s, sala=%s, fechaHora=%s, estado=%s, costo=%s}",
                paciente.getNombreCompleto(),
                medico.getNombreCompleto(),
                sala.getNumero(),
                fechaHora,
                estado.getDescripcion(),
                costo);
    }
}
