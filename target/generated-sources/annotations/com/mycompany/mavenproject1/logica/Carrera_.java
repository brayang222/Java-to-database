package com.mycompany.mavenproject1.logica;

import com.mycompany.mavenproject1.logica.Materia;
import com.mycompany.mavenproject1.logica.Profesor;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-08-29T09:37:49", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Carrera.class)
public class Carrera_ { 

    public static volatile ListAttribute<Carrera, Materia> listaMaterias;
    public static volatile SingularAttribute<Carrera, Profesor> profesor;
    public static volatile SingularAttribute<Carrera, Integer> id;
    public static volatile SingularAttribute<Carrera, String> nombre;

}