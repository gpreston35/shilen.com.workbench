ALTER TABLE sensors.alerts
  ADD COLUMN enabled VARCHAR(1) NULL DEFAULT 'Y' AFTER notification_type;

UPDATE sensors.alerts
SET enabled = 'Y'
WHERE enabled IS NULL;
