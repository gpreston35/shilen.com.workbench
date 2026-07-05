SET @has_enabled := (
  SELECT COUNT(*)
  FROM information_schema.columns
  WHERE table_schema = 'sensors'
    AND table_name = 'alert_recipients'
    AND column_name = 'enabled'
);
SET @sql := IF(
  @has_enabled = 0,
  'ALTER TABLE sensors.alert_recipients ADD COLUMN enabled VARCHAR(1) NULL DEFAULT ''Y'' AFTER recipient',
  'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @has_user_id := (
  SELECT COUNT(*)
  FROM information_schema.columns
  WHERE table_schema = 'sensors'
    AND table_name = 'alert_recipients'
    AND column_name = 'user_id'
);
SET @sql := IF(
  @has_user_id = 0,
  'ALTER TABLE sensors.alert_recipients ADD COLUMN user_id INT NULL AFTER cycle_id',
  'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE sensors.alert_recipients
SET enabled = 'Y'
WHERE enabled IS NULL OR TRIM(enabled) = '';

UPDATE sensors.alert_recipients ar
JOIN operations.user u
  ON UPPER(IFNULL(u.sms_email, '')) = UPPER(IFNULL(ar.recipient, ''))
SET ar.user_id = u.id
WHERE ar.user_id IS NULL;
