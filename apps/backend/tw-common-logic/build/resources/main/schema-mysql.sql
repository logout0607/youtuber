CREATE TABLE advertisement (
  advertisement_idx            INTEGER      NOT NULL AUTO_INCREMENT,
  contractor_date              DATETIME(6),
  contractor_timezone          VARCHAR(255),
  contractor_name              VARCHAR(255) NOT NULL,
  created_date                 DATETIME(6)  NOT NULL,
  created_date_timezone        VARCHAR(255) NOT NULL,
  enabled                      BOOLEAN               DEFAULT TRUE,
  end_date                     DATETIME(6)  NOT NULL,
  end_date_timezone            VARCHAR(255) NOT NULL,
  last_modified_date           DATETIME(6),
  last_modified_date_timezone  VARCHAR(255),
  max_exposure_point           INTEGER      NOT NULL,
  name                         VARCHAR(255) NOT NULL,
  pay_type_code                VARCHAR(255),
  pay_type_price               BIGINT       NOT NULL,
  start_date                   DATETIME(6)  NOT NULL,
  start_date_timezone          VARCHAR(255) NOT NULL,
  created_user_unique_id       VARCHAR(255),
  image                        BIGINT,
  last_modified_user_unique_id VARCHAR(255),
  video_media                  BIGINT,
  PRIMARY KEY (advertisement_idx)
)
  ENGINE = InnoDB;

CREATE TABLE cash (
  cash_idx        INTEGER NOT NULL AUTO_INCREMENT,
  cancel_yn       BIT,
  cash            INTEGER NOT NULL,
  purchase_type   INTEGER,
  reg_dt          DATETIME(6),
  reg_dt_timezone VARCHAR(255),
  upd_dt          DATETIME(6),
  upd_dt_timezone VARCHAR(255),
  cash_point      INTEGER,
  user_unique_id  VARCHAR(255),
  PRIMARY KEY (cash_idx)
)
  ENGINE = InnoDB;

CREATE TABLE celeb (
  celeb_idx                    INTEGER              NOT NULL AUTO_INCREMENT,
  celeb_email                  VARCHAR(100)         NOT NULL,
  enabled                      BOOLEAN DEFAULT TRUE NOT NULL,
  official_name                VARCHAR(255)         NOT NULL,
  password                     VARCHAR(255)         NOT NULL,
  reg_dt                       DATETIME(6),
  reg_dt_timezone              VARCHAR(255),
  upd_dt                       DATETIME(6),
  upd_dt_timezone              VARCHAR(255),
  celeb_logo                   BIGINT               NOT NULL,
  celeb_photo                  BIGINT               NOT NULL,
  charity_idx                  INTEGER,
  created_user_unique_id       VARCHAR(255),
  donate_idx                   INTEGER,
  last_modified_user_unique_id VARCHAR(255),
  PRIMARY KEY (celeb_idx)
)
  ENGINE = InnoDB;

CREATE TABLE celeb_name (
  celeb_idx INTEGER      NOT NULL,
  name      VARCHAR(255),
  locale    VARCHAR(255) NOT NULL,
  PRIMARY KEY (celeb_idx, locale)
)
  ENGINE = InnoDB;

CREATE TABLE charity (
  charity_idx                  INTEGER      NOT NULL AUTO_INCREMENT,
  address                      VARCHAR(255) NOT NULL,
  created_date                 DATETIME(6)  NOT NULL,
  created_date_timezone        VARCHAR(255) NOT NULL,
  enabled                      BOOLEAN               DEFAULT TRUE,
  establish_date               DATETIME(6),
  establish_timezone           VARCHAR(255),
  history                      TEXT         NOT NULL,
  last_modified_date           DATETIME(6),
  last_modified_date_timezone  VARCHAR(255),
  name                         VARCHAR(255) NOT NULL,
  nation_type_code             VARCHAR(255),
  created_user_unique_id       VARCHAR(255),
  image                        BIGINT,
  last_modified_user_unique_id VARCHAR(255),
  PRIMARY KEY (charity_idx)
)
  ENGINE = InnoDB;

CREATE TABLE chat_log (
  chat_log_idx    INTEGER NOT NULL AUTO_INCREMENT,
  celeb_yn        BIT     NOT NULL,
  content         TEXT,
  reg_dt          DATETIME(6),
  reg_dt_timezone VARCHAR(255),
  celeb_idx       INTEGER NOT NULL,
  video_idx       BIGINT,
  PRIMARY KEY (chat_log_idx)
)
  ENGINE = InnoDB;

CREATE TABLE donate (
  donate_idx                   INTEGER      NOT NULL AUTO_INCREMENT,
  created_date                 DATETIME(6)  NOT NULL,
  created_date_timezone        VARCHAR(255) NOT NULL,
  donate_enabled               BOOLEAN               DEFAULT TRUE,
  donate_name                  VARCHAR(255) NOT NULL,
  donate_visible               BOOLEAN               DEFAULT TRUE,
  end_date                     DATETIME(6)  NOT NULL,
  end_date_timezone            VARCHAR(255) NOT NULL,
  last_modified_date           DATETIME(6),
  last_modified_date_timezone  VARCHAR(255),
  require_point                INTEGER      NOT NULL,
  start_date                   DATETIME(6)  NOT NULL,
  start_date_timezone          VARCHAR(255) NOT NULL,
  created_user_unique_id       VARCHAR(255),
  image                        BIGINT,
  last_modified_user_unique_id VARCHAR(255),
  PRIMARY KEY (donate_idx)
)
  ENGINE = InnoDB;

CREATE TABLE donate_celeb (
  donate_idx INTEGER NOT NULL,
  celeb_idx  INTEGER NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE donate_charity (
  charity_idx INTEGER NOT NULL,
  donate_idx  INTEGER NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE donate_log (
  donate_log_idx  INTEGER NOT NULL AUTO_INCREMENT,
  reg_dt          DATETIME(6),
  reg_dt_timezone VARCHAR(255),
  upd_dt          DATETIME(6),
  upd_dt_timezone VARCHAR(255),
  celeb_idx       INTEGER NOT NULL,
  donate_idx      INTEGER NOT NULL,
  cash_point      INTEGER,
  user_unique_id  VARCHAR(255),
  video_idx       BIGINT,
  PRIMARY KEY (donate_log_idx)
)
  ENGINE = InnoDB;

CREATE TABLE donate_name (
  donate_idx INTEGER      NOT NULL,
  name       VARCHAR(255),
  locale     VARCHAR(255) NOT NULL,
  PRIMARY KEY (donate_idx, locale)
)
  ENGINE = InnoDB;

CREATE TABLE image (
  image_idx                    BIGINT       NOT NULL AUTO_INCREMENT,
  content_type                 VARCHAR(255),
  created_date                 DATETIME(6)  NOT NULL,
  created_date_timezone        VARCHAR(255) NOT NULL,
  enabled                      BIT          NOT NULL,
  image_name                   VARCHAR(255) NOT NULL,
  image_size                   BIGINT       NOT NULL,
  last_modified_date           DATETIME(6),
  last_modified_date_timezone  VARCHAR(255),
  sort_order                   INTEGER,
  stored_image_name            VARCHAR(255) NOT NULL,
  created_user_unique_id       VARCHAR(255),
  image_group_image_group_idx  BIGINT,
  last_modified_user_unique_id VARCHAR(255),
  PRIMARY KEY (image_idx)
)
  ENGINE = InnoDB;

CREATE TABLE image_group (
  image_group_idx              BIGINT       NOT NULL AUTO_INCREMENT,
  created_date                 DATETIME(6)  NOT NULL,
  created_date_timezone        VARCHAR(255) NOT NULL,
  last_modified_date           DATETIME(6),
  last_modified_date_timezone  VARCHAR(255),
  created_user_unique_id       VARCHAR(255),
  last_modified_user_unique_id VARCHAR(255),
  PRIMARY KEY (image_group_idx)
)
  ENGINE = InnoDB;

CREATE TABLE live (
  live_idx                     BIGINT               NOT NULL AUTO_INCREMENT,
  content                      VARCHAR(255),
  enabled                      BOOLEAN DEFAULT TRUE NOT NULL,
  end_date                     DATETIME(6)          NOT NULL,
  end_date_timezone            VARCHAR(255)         NOT NULL,
  landscape                    BOOLEAN DEFAULT TRUE NOT NULL,
  reg_dt                       DATETIME(6),
  reg_dt_timezone              VARCHAR(255),
  rtmp_address                 VARCHAR(255)         NOT NULL,
  start_date                   DATETIME(6)          NOT NULL,
  start_date_timezone          VARCHAR(255)         NOT NULL,
  status                       INTEGER              NOT NULL,
  subject                      VARCHAR(255)         NOT NULL,
  upd_dt                       DATETIME(6),
  upd_dt_timezone              VARCHAR(255),
  view_count                   INTEGER,
  celeb_idx                    INTEGER              NOT NULL,
  created_user_unique_id       VARCHAR(255),
  video_thumb                  BIGINT,
  last_modified_user_unique_id VARCHAR(255),
  PRIMARY KEY (live_idx)
)
  ENGINE = InnoDB;

CREATE TABLE media (
  idx                          BIGINT               NOT NULL,
  content_type                 VARCHAR(255),
  created_date                 DATETIME(6)          NOT NULL,
  created_date_timezone        VARCHAR(255)         NOT NULL,
  enabled                      BOOLEAN DEFAULT TRUE NOT NULL,
  last_modified_date           DATETIME(6),
  last_modified_date_timezone  VARCHAR(255),
  name                         VARCHAR(255),
  time                         BIGINT,
  s3_path                      VARCHAR(255),
  size                         BIGINT,
  stored_name                  VARCHAR(255),
  created_user_unique_id       VARCHAR(255),
  last_modified_user_unique_id VARCHAR(255),
  PRIMARY KEY (idx)
)
  ENGINE = InnoDB;

CREATE TABLE pay_type (
  pay_type_idx      INTEGER NOT NULL AUTO_INCREMENT,
  pay_type_code     VARCHAR(255),
  advertisement_idx INTEGER NOT NULL,
  PRIMARY KEY (pay_type_idx)
)
  ENGINE = InnoDB;

CREATE TABLE point (
  point_idx       INTEGER      NOT NULL AUTO_INCREMENT,
  point           INTEGER      NOT NULL,
  point_type      VARCHAR(255) NOT NULL,
  reg_dt          DATETIME(6),
  reg_dt_timezone VARCHAR(255),
  upd_dt          DATETIME(6),
  upd_dt_timezone VARCHAR(255),
  user_unique_id  VARCHAR(255),
  PRIMARY KEY (point_idx)
)
  ENGINE = InnoDB;

CREATE TABLE setting (
  setting_idx   INTEGER NOT NULL AUTO_INCREMENT,
  setting_type  VARCHAR(255),
  setting_value INTEGER NOT NULL,
  PRIMARY KEY (setting_idx)
)
  ENGINE = InnoDB;

CREATE TABLE user (
  unique_id                   VARCHAR(255) NOT NULL,
  created_date                DATETIME(6)  NOT NULL,
  created_date_timezone       VARCHAR(255) NOT NULL,
  email                       VARCHAR(150),
  enabled                     BIT          NOT NULL,
  last_modified_date          DATETIME(6),
  last_modified_date_timezone VARCHAR(255),
  password                    VARCHAR(200) NOT NULL,
  user_name                   VARCHAR(255) NOT NULL,
  celeb_idx                   INTEGER,
  user_social_idx             INTEGER,
  PRIMARY KEY (unique_id)
)
  ENGINE = InnoDB;

CREATE TABLE user_role (
  user_role_idx INTEGER      NOT NULL AUTO_INCREMENT,
  role          VARCHAR(45)  NOT NULL,
  unique_id     VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_role_idx)
)
  ENGINE = InnoDB;

CREATE TABLE user_social (
  user_social_idx  INTEGER      NOT NULL AUTO_INCREMENT,
  access_token     VARCHAR(255),
  display_nm       VARCHAR(255),
  expire_time      BIGINT,
  image_url        VARCHAR(255),
  profile_url      VARCHAR(255),
  provider_id      VARCHAR(255) NOT NULL,
  provider_user_id VARCHAR(255),
  rank             INTEGER,
  refresh_token    VARCHAR(255),
  secret           VARCHAR(255),
  PRIMARY KEY (user_social_idx)
)
  ENGINE = InnoDB;

CREATE TABLE video (
  video_idx                    BIGINT               NOT NULL AUTO_INCREMENT,
  content                      VARCHAR(255),
  enabled                      BOOLEAN DEFAULT TRUE NOT NULL,
  reg_dt                       DATETIME(6),
  reg_dt_timezone              VARCHAR(255),
  subject                      VARCHAR(255)         NOT NULL,
  upd_dt                       DATETIME(6),
  upd_dt_timezone              VARCHAR(255),
  view_count                   INTEGER,
  celeb_idx                    INTEGER              NOT NULL,
  created_user_unique_id       VARCHAR(255),
  video_thumb                  BIGINT,
  last_modified_user_unique_id VARCHAR(255),
  video_media                  BIGINT,
  PRIMARY KEY (video_idx)
)
  ENGINE = InnoDB;

alter table celeb add constraint UK_n3ullh9ghevwmmvdb0k2dlh4y unique (celeb_email);
alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table advertisement add constraint FKe5ap01uaylnsjp9e1fooiqs6m foreign key (created_user_unique_id) references user (unique_id);
alter table advertisement add constraint FK4dl7p9k4wt37hoqeeqqo1jr83 foreign key (image) references image_group (image_group_idx);
alter table advertisement add constraint FKd98d3k9lorsi8fegam8ch7por foreign key (last_modified_user_unique_id) references user (unique_id);
alter table advertisement add constraint FK7vssh8amuqgbp14pbhy3trmsc foreign key (video_media) references media (idx);
alter table cash add constraint FKkry06tqgscgevgg5e052fq9l6 foreign key (cash_point) references point (point_idx);
alter table cash add constraint FKrc0rxtw51t5y3o5ewlgrniu6b foreign key (user_unique_id) references user (unique_id);
alter table celeb add constraint FK4vasr6xm38actufnsoeltfj9j foreign key (celeb_logo) references image_group (image_group_idx);
alter table celeb add constraint FKj1modwn45mc79r6en1mgdxtwf foreign key (celeb_photo) references image_group (image_group_idx);
alter table celeb add constraint FKt9o4qhuyshwacx44d23h54jj8 foreign key (charity_idx) references charity (charity_idx);
alter table celeb add constraint FK6eoy751gy10s9wjttguhqooho foreign key (created_user_unique_id) references user (unique_id);
alter table celeb add constraint FKk7ou92tw57gnf4g26navrla9b foreign key (donate_idx) references donate (donate_idx);
alter table celeb add constraint FKsv2josmqninkgi1s8qddiq9eg foreign key (last_modified_user_unique_id) references user (unique_id);
alter table celeb_name add constraint FKkp43r9bm9ctyh3s37xdj1i1ri foreign key (celeb_idx) references celeb (celeb_idx);
alter table charity add constraint FKnbhjw2ye0pc1xgbyho886x8nh foreign key (created_user_unique_id) references user (unique_id);
alter table charity add constraint FKqtan69u6ka318e4twkf380811 foreign key (image) references image_group (image_group_idx);
alter table charity add constraint FKlxjtisj3urjykqr4pxwtmoiqk foreign key (last_modified_user_unique_id) references user (unique_id);
alter table chat_log add constraint FK55joxqojhr885o6cyry8i99f3 foreign key (celeb_idx) references celeb (celeb_idx);
alter table chat_log add constraint FKllr8q5acraa5mduhspdtpj6eo foreign key (video_idx) references video (video_idx);
alter table donate add constraint FKck40u8nc56igq6qp98tnj63sr foreign key (created_user_unique_id) references user (unique_id);
alter table donate add constraint FKcgx9puyufbypnbwk47q4e676g foreign key (image) references image_group (image_group_idx);
alter table donate add constraint FKpfbeyk4v9e6x6cdnu6737poq foreign key (last_modified_user_unique_id) references user (unique_id);
alter table donate_celeb add constraint FKfdvocxdisximds91ga4bvab3v foreign key (celeb_idx) references celeb (celeb_idx);
alter table donate_celeb add constraint FK53con63abwvhpojy5ch73guq7 foreign key (donate_idx) references donate (donate_idx);
alter table donate_charity add constraint FK54trex1r5jjks90kwl242kvst foreign key (donate_idx) references donate (donate_idx);
alter table donate_charity add constraint FK3ij761oooasu5290w1qrq4iof foreign key (charity_idx) references charity (charity_idx);
alter table donate_log add constraint FKjt95u6xowi2xy6ftei9mu3qkt foreign key (celeb_idx) references celeb (celeb_idx);
alter table donate_log add constraint FKbsw7w1younnaltxueooc8perg foreign key (donate_idx) references donate (donate_idx);
alter table donate_log add constraint FKdkv3mt7kxn2m7f2kisxflkpnm foreign key (cash_point) references point (point_idx);
alter table donate_log add constraint FKc2di6ixsvab27ky4x9a9nl1sw foreign key (user_unique_id) references user (unique_id);
alter table donate_log add constraint FK7ode08646la5jg14pr8x5s1v5 foreign key (video_idx) references video (video_idx);
alter table donate_name add constraint FKrrhx3huv2otccg5p82vrlr4lo foreign key (donate_idx) references donate (donate_idx);
alter table image add constraint FK86qodr1m07qidestjg2m984y2 foreign key (created_user_unique_id) references user (unique_id);
alter table image add constraint FKt3h2iic1rvsl72tnjb00pbnkj foreign key (image_group_image_group_idx) references image_group (image_group_idx);
alter table image add constraint FKj3xkh0eaw66f6ykcan6u0a6jc foreign key (last_modified_user_unique_id) references user (unique_id);
alter table image_group add constraint FKj3ash5xme5njjsfs2epnltriy foreign key (created_user_unique_id) references user (unique_id);
alter table image_group add constraint FKk35mxhl1tdq5bv0qw986vfjq foreign key (last_modified_user_unique_id) references user (unique_id);
alter table live add constraint FK11y8uxeqst4a2xi3u2mbg7h3s foreign key (celeb_idx) references celeb (celeb_idx);
alter table live add constraint FKtg0uem3lac0k8mmwy0e2ia5ae foreign key (created_user_unique_id) references user (unique_id);
alter table live add constraint FKrd4xf9de83jccl77u34etdru8 foreign key (video_thumb) references image_group (image_group_idx);
alter table live add constraint FKg63ke6dpsn8b94p5c9de7a5ee foreign key (last_modified_user_unique_id) references user (unique_id);
alter table media add constraint FKbh4y3jjqsp5ko2swupy1w3kdw foreign key (created_user_unique_id) references user (unique_id);
alter table media add constraint FKdydkblrrfwytxf281ty5gx41x foreign key (last_modified_user_unique_id) references user (unique_id);
alter table pay_type add constraint FKhydex1sid9vmlbv993yyk567u foreign key (advertisement_idx) references advertisement (advertisement_idx);
alter table point add constraint FKnd0pf56t9u5vomfal18mumk09 foreign key (user_unique_id) references user (unique_id);
alter table user add constraint FK9om1u6lfwx6ug0rk8a0u41c0j foreign key (celeb_idx) references celeb (celeb_idx);
alter table user add constraint FKpadrp3nfifq0vhbqy9lfhc2ua foreign key (user_social_idx) references user_social (user_social_idx);
alter table user_role add constraint FKjcnydqdgt44s5fiqjbtup4qf0 foreign key (unique_id) references user (unique_id);
alter table video add constraint FK7d2fbhb2my329ifyjwb6gyxsh foreign key (celeb_idx) references celeb (celeb_idx);
alter table video add constraint FKr84ben9d12h3940dpnaqav4ch foreign key (created_user_unique_id) references user (unique_id);
alter table video add constraint FKci8vb2ch2nuck617qfc842gct foreign key (video_thumb) references image_group (image_group_idx);
alter table video add constraint FK8yur6m0pqglbyfrr87iva7tr1 foreign key (last_modified_user_unique_id) references user (unique_id);
alter table video add constraint FKw96akyc0fyevjri0y34kjsae foreign key (video_media) references media (idx);
