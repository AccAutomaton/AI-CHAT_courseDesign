create table USER
(
    UID         int auto_increment
        primary key,
    USERNAME    varchar(64)                not null,
    PASSWORD    varchar(256)               not null,
    NICKNAME    varchar(64)   default ''   not null,
    STATUS      int                        not null,
    PHONE       varchar(64)   default ''   not null,
    USER_TYPE   int                        not null,
    BALANCE     int           default 0.00 not null,
    CREATE_TIME timestamp                  not null,
    UPDATE_TIME timestamp                  not null,
    DELETE_FLAG int           default 0    not null,
    constraint SystemUser_pk2
        unique (UID),
    constraint SystemUser_pk3
        unique (USERNAME)
) auto_increment = 100000001;

create table USER_VIP_LEVEL
(
    UID       int           not null
        primary key,
    VIP_LEVEL int default 1 not null,
    constraint USER_VIP_LEVEL_pk2
        unique (UID)
);

create table VIP_LEVEL_DESCRIPTION
(
    ID          int auto_increment
        primary key,
    DESCRIPTION varchar(64) default '' not null,
    constraint VIP_LEVEL_pk2
        unique (ID)
);

create table DIALOG
(
    ID          int auto_increment
        primary key,
    UID         int                     not null,
    TITLE       varchar(256) default '' not null,
    DIALOG_LIST json                    not null,
    CREATE_TIME timestamp               not null,
    UPDATE_TIME timestamp               not null,
    DELETE_FLAG int          default 0  not null,
    constraint DIALOG_pk2
        unique (ID)
) auto_increment = 100000001;

create table RECHARGE
(
    ID          int auto_increment
            primary key,
    UID         int                        not null,
    VALUE       int           default 0.00 not null,
    BALANCE     int           default 0.00 not null,
    CREATE_TIME timestamp                  not null,
    constraint RECHARGE_pk2
        unique (ID)
) auto_increment = 100000001;