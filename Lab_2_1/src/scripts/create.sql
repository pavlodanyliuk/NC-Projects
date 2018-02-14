DROP TABLE params;
DROP TABLE attributes;
DROP TABLE objects;
DROP TABLE types;

-- TODO: REFERENCES IN PARAMETRS
CREATE TABLE types(
    id VARCHAR2(12) NOT NULL,
    name VARCHAR2(22) NOT NULL,
    parent_id VARCHAR2(12),
    CONSTRAINT types_id_pk PRIMARY KEY(id)
    );
    
CREATE TABLE objects(
    id VARCHAR2(12) NOT NULL,
    type_id VARCHAR2(12) NOT NULL,
    -- parent id for knowing if it is an objects into other object
    parent_id VARCHAR2(12),
    CONSTRAINT objects_id_pk PRIMARY KEY(id),
    CONSTRAINT types_typeId_fk FOREIGN KEY(type_id)
        REFERENCES types(id)
    );

CREATE TABLE attributes(
    id VARCHAR2(12) NOT NULL,
    name VARCHAR2(20) NOT NULL,
    type_id VARCHAR2(12) NOT NULL,
    CONSTRAINT attributes_id_pk PRIMARY KEY(id),
    CONSTRAINT attributes_typeId_fk FOREIGN KEY(type_id)
        REFERENCES types(id)
    );
    
CREATE TABLE params(
    text_value VARCHAR2(200),
    number_value INTEGER,
    data_value DATE,
    reference_value VARCHAR2(12),
    attr_id VARCHAR2(12) NOT NULL,
    obj_id VARCHAR2(12) NOT NULL,
    CONSTRAINT params_attId_objId_pk PRIMARY KEY(attr_id, obj_id),
    CONSTRAINT params_attrId_fk FOREIGN KEY(attr_id)
        REFERENCES attributes(id),
    CONSTRAINT params_objId_fk FOREIGN KEY(obj_id)
        REFERENCES objects(id),
     CONSTRAINT params_referenceValue_fk FOREIGN KEY(reference_value)
        REFERENCES objects(id)
    );
    
