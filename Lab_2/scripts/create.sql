DROP TABLE "params";
DROP TABLE "attributes";
DROP TABLE "objects";
DROP TABLE "types";

-- TODO: REFERENCES IN PARAMETRS
CREATE TABLE "types"(
    "id" NUMBER(5) NOT NULL,
    "name" VARCHAR2(22) NOT NULL,
    "parent_id" NUMBER(5),
    CONSTRAINT "types_id_pk" PRIMARY KEY("id")
    );
    
CREATE TABLE "objects"(
    "id" NUMBER(5) NOT NULL,
    "name" VARCHAR2(20) NOT NULL,
    "type_id" NUMBER(5) NOT NULL,
    "parent_id" NUMBER(5),
    CONSTRAINT "objects_id_pk" PRIMARY KEY("id"),
    CONSTRAINT "types_typeId_fk" FOREIGN KEY("type_id")
        REFERENCES "types"("id")
    );

CREATE TABLE "attributes"(
    "id" NUMBER(5) NOT NULL,
    "name" VARCHAR2(20) NOT NULL,
    "type_id" NUMBER(5) NOT NULL,
    CONSTRAINT "attributes_id_pk" PRIMARY KEY("id"),
    CONSTRAINT "attributes_typeId_fk" FOREIGN KEY("type_id")
        REFERENCES "types"("id")
    );
    
CREATE TABLE "params"(
    "text_value" VARCHAR2(200),
    "number_value" INTEGER,
    "data_value" DATE,
    "attr_id" NUMBER(5) NOT NULL,
    "obj_id" NUMBER(5) NOT NULL,
    CONSTRAINT "params_attrId_fk" FOREIGN KEY("attr_id")
        REFERENCES "attributes"("id"),
    CONSTRAINT "params_objId_fk" FOREIGN KEY("obj_id")
        REFERENCES "objects"("id")
    );
    
