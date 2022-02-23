
CREATE TABLE IF NOT EXISTS plot (
  customer_id               VARCHAR(50)                 NOT NULL,
  plot_id                   VARCHAR(50)                 NOT NULL,
  owner_id                  VARCHAR(50)                 NOT NULL,
  plot_name                 VARCHAR(100)                NOT NULL,
  description               VARCHAR(200),
  address                   text,
  plot_size                 text,
  version                   VARCHAR(50)                 NOT NULL,
  PRIMARY KEY (customer_id, plot_id)
);

CREATE TABLE IF NOT EXISTS plot_configuration (
    customer_id             VARCHAR(50)                 NOT NULL,
    plot_id                 VARCHAR(50)                 NOT NULL,
    plot_name               VARCHAR(100)                NOT NULL,
    description             VARCHAR(200),
    soil_type               VARCHAR(100),
    crops                   text,
    irrigation              text                        NOT NULL,
    average_moisture_level  VARCHAR(50),
    version                 VARCHAR(50)                 NOT NULL,
    PRIMARY KEY (customer_id, plot_id)
);

CREATE TABLE IF NOT EXISTS schedule (
    customer_id             VARCHAR(50)                 NOT NULL,
    plot_id                 VARCHAR(50)                 NOT NULL,
    schedule_id             VARCHAR(50)                 NOT NULL,
    schedule_name           VARCHAR(100)                NOT NULL,
    description             VARCHAR(200),
    start_time              timestamp without time zone NOT NULL,
    end_time                timestamp without time zone NOT NULL,
    version                 VARCHAR(50)                 NOT NULL,
    PRIMARY KEY (customer_id, plot_id, schedule_id)
);

CREATE TABLE IF NOT EXISTS device_schedule_state (
    customer_id             VARCHAR(50)                 NOT NULL,
    plot_id                 VARCHAR(50)                 NOT NULL,
    schedule_id             VARCHAR(50)                 NOT NULL,
    device_id               VARCHAR(50)                 NOT NULL,
    device_state            VARCHAR(100)                NOT NULL,
    irrigation_state        VARCHAR(100)                NOT NULL,
    retry_count             integer                     NOT NULL,
    PRIMARY KEY (customer_id, plot_id, schedule_id, device_id)
);
