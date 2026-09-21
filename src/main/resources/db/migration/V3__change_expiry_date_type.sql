alter table banking_core.card
alter
column expiry_date type date using expiry_date::date;

alter table banking_core.card
    alter column expiry_date set default (current_date + '5 years'::interval);
