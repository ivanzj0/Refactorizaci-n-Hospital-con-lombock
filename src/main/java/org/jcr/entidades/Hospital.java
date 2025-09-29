package main.java.org.jcr.entidades;

import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class Hospital implements Serializable {

    @NonNull
    private final String nombre;
    @NonNull
    private final String direccion;
    @NonNull
    private final String telefono;

    @EqualsAndHashCode.Exclude
    private final List<Departamento> departamentos = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    private final List<Paciente> pacientes = new ArrayList<>();

    public void agregarDepartamento(Departamento departamento) {
        if (departamento != null && !departamentos.contains(departamento)) {
            departamentos.add(departamento);
            departamento.setHospital(this);
        }
    }

    public void agregarPaciente(Paciente paciente) {
        if (paciente != null && !pacientes.contains(paciente)) {
            pacientes.add(paciente);
            paciente.setHospital(this);
        }
    }

    public List<Departamento> getDepartamentos() {
        return Collections.unmodifiableList(departamentos);
    }

    public List<Paciente> getPacientes() {
        return Collections.unmodifiableList(pacientes);
    }

    List<Departamento> getInternalDepartamentos() {
        return departamentos;
    }

    List<Paciente> getInternalPacientes() {
        return pacientes;
    }

    @Override
    public String toString() {
        return String.format("Hospital{nombre='%s', direccion='%s', telefono='%s'}",
                nombre, direccion, telefono);
    }
}
