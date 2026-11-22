ALTER TABLE cards ADD COLUMN card_design VARCHAR(32);

UPDATE cards SET card_design = 'Синяя классика'     WHERE id = 2001;
UPDATE cards SET card_design = 'Чёрная матовая'     WHERE id = 2002;
UPDATE cards SET card_design = 'Зарплатная стандарт' WHERE id = 2003;
UPDATE cards SET card_design = 'Детская радуга'     WHERE id = 2004;
UPDATE cards SET card_design = 'Премиум тёмная'     WHERE id = 2005;
UPDATE cards SET card_design = 'Синяя классика'     WHERE id = 2006;
UPDATE cards SET card_design = 'Красная кредитка'   WHERE id = 2007;
UPDATE cards SET card_design = 'Неон цифровой'      WHERE id = 2008;