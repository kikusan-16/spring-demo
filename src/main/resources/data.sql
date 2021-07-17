/*部署マスタ*/
INSERT INTO m_department(
  department_id,department_name
)VALUES
(1,'システム管理部'),
(2,'営業部');

/*ユーザーマスタ*/
INSERT INTO m_user(
  user_id,
  password,
  user_name,
  birthday,
  age,
  gender,
  department_id,
  role
) VALUES
('system@co.jp','use initial test user password','システム管理者','20000101',21,1,1,'ROLE_ADMIN'),
('user@co.jp','use initial test user password','ユーザー1','20000101',21,2,2,'ROLE_GENERAL');

/*給料テーブル*/
INSERT INTO t_salary(
  user_id, year_month, salary
)VALUES
('user@co.jp','2020/11',280000),
('user@co.jp','2020/12',290000),
('user@co.jp','2021/01',300000);
