DROP TABLE GRANTATTR;
DROP TABLE GRANTTYPES;
DROP TABLE GRANTOBJECTS;

CREATE TABLE GRANTATTR(
    role VARCHAR2(22) NOT NULL,
    attr_id VARCHAR2(12) NOT NULL,
    read NUMBER(1) NOT NULL CHECK(READ=1 OR READ=0),
    write NUMBER(1) NOT NULL CHECK(write=1 OR write=0),
    CONSTRAINT grantattr_id_pk PRIMARY KEY(role, attr_id),
    CONSTRAINT grantattr_attrId_fk FOREIGN KEY(attr_id)
        REFERENCES ATTRIBUTES(id)

    );
    
CREATE TABLE GRANTTYPES(
    role VARCHAR2(22) NOT NULL,
    type_id VARCHAR2(12) NOT NULL,
    read NUMBER(1) NOT NULL CHECK(READ=1 OR READ=0),
    write NUMBER(1) NOT NULL CHECK(write=1 OR write=0),
    CONSTRAINT granttypes_id_pk PRIMARY KEY(role, type_id),
    CONSTRAINT granttypes_typeId_fk FOREIGN KEY(type_id)
        REFERENCES TYPES(id)
    );
    
CREATE TABLE GRANTOBJECTS(
    role VARCHAR2(22) NOT NULL,
    obj_id VARCHAR2(12) NOT NULL,
    read NUMBER(1) NOT NULL CHECK(READ=1 OR READ=0),
    write NUMBER(1) NOT NULL CHECK(write=1 OR write=0),
    CONSTRAINT grantobjects_id_pk PRIMARY KEY(role, obj_id),
    CONSTRAINT grantobjects_objId_fk FOREIGN KEY(obj_id)
        REFERENCES OBJECTS(id)

    );
    
