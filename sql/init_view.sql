create definer = root@`%` view USER_VIP_DESCRIPTION as
select `AI_CHAT`.`USER_VIP_LEVEL`.`UID`                AS `UID`,
       `AI_CHAT`.`USER_VIP_LEVEL`.`VIP_LEVEL`          AS `VIP_LEVEL`,
       `AI_CHAT`.`VIP_LEVEL_DESCRIPTION`.`DESCRIPTION` AS `DESCRIPTION`
from (`AI_CHAT`.`USER_VIP_LEVEL` left join `AI_CHAT`.`VIP_LEVEL_DESCRIPTION`
      on ((`AI_CHAT`.`USER_VIP_LEVEL`.`VIP_LEVEL` = `AI_CHAT`.`VIP_LEVEL_DESCRIPTION`.`ID`)));
create definer = root@`%` view USER_DETAIL as
select `AI_CHAT`.`USER`.`UID`                         AS `UID`,
       `AI_CHAT`.`USER`.`USERNAME`                    AS `USERNAME`,
       `AI_CHAT`.`USER`.`NICKNAME`                    AS `NICKNAME`,
       `AI_CHAT`.`USER`.`STATUS`                      AS `STATUS`,
       `AI_CHAT`.`USER`.`PHONE`                       AS `PHONE`,
       `AI_CHAT`.`USER`.`USER_TYPE`                   AS `USER_TYPE`,
       `AI_CHAT`.`USER`.`BALANCE`                     AS `BALANCE`,
       `AI_CHAT`.`USER`.`CREATE_TIME`                 AS `CREATE_TIME`,
       `AI_CHAT`.`USER`.`UPDATE_TIME`                 AS `UPDATE_TIME`,
       `AI_CHAT`.`USER`.`DELETE_FLAG`                 AS `DELETE_FLAG`,
       `AI_CHAT`.`USER_VIP_DESCRIPTION`.`DESCRIPTION` AS `VIP_LEVEL`
from (`AI_CHAT`.`USER` join `AI_CHAT`.`USER_VIP_DESCRIPTION`)
where ((`AI_CHAT`.`USER`.`UID` = `AI_CHAT`.`USER_VIP_DESCRIPTION`.`UID`) and (`AI_CHAT`.`USER`.`DELETE_FLAG` = 0));

