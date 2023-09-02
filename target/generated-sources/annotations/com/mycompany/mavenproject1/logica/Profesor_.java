package com.mycompany.mavenproject1.logica;

import com.mycompany.mavenproject1.logica.Carrera;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-08-29T09:37:49", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Profesor.class)
public class Profesor_ { 

    public static volatile SingularAttribute<Profesor, Integer> id;
    public static volatile ListAttribute<Profesor, Carrera> listaCarreras;
    public static volatile SingularAttribute<Profesor, String> Name;

}