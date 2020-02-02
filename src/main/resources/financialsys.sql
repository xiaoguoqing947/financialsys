create schema if not exists financialsys collate utf8_general_ci;

create table if not exists bookmark
(
    id int auto_increment
        primary key,
    opr_time datetime null comment '操作时间',
    username varchar(50) null comment '用户名',
    title varchar(255) null comment '书签内容'
);

create table if not exists dictionary
(
    id int auto_increment
        primary key,
    dcis_code varchar(255) null,
    dcis_name varchar(255) null,
    key_value varchar(255) null,
    key_name varchar(255) null,
    remarks varchar(255) null,
    opr_time datetime null
);

create table if not exists expend
(
    expend_id int auto_increment
        primary key,
    expend_type_id int null comment '支出类型编码',
    expend_use varchar(50) null comment '支出方式',
    pay_method varchar(2) null comment '付款方式',
    pay_account varchar(20) null comment '付款账号',
    expend_price double null comment '付款金额',
    expend_date datetime null comment '付款日期',
    expend_user varchar(50) null comment '支出用户',
    expend_desc varchar(128) null comment '支出描述'
);

create table if not exists expendtype
(
    expend_type_id int auto_increment comment '支出类别编码'
        primary key,
    enpend_type varchar(50) null comment '支出类别名称'
);

create table if not exists income
(
    income_id int auto_increment
        primary key,
    income_type_id int null comment '收入类别编码',
    income_source varchar(50) null comment '收入来源',
    pay_method varchar(2) null comment '收款方式',
    pay_account varchar(20) null comment '收款账号',
    income_price double null comment '收入金额',
    income_date datetime null comment '收入日期',
    income_user varchar(50) null comment '收入用户',
    income_desc varchar(128) null comment '收入描述'
);

create table if not exists incometype
(
    income_type_id int auto_increment comment '收入类别编码'
        primary key,
    income_type varchar(50) null comment '收入类别名称'
);

create table if not exists logs
(
    id bigint auto_increment
        primary key,
    create_time datetime null comment '创建时间',
    username varchar(50) default '0' null comment '用户名',
    update_time datetime null comment '更新时间',
    log_desc varchar(1023) null comment '日志描述',
    ip_address varchar(127) default '' null comment '用户ID地址',
    log_key varchar(1023) default '' null,
    type int null
)
    engine=MyISAM collate=utf8mb4_bin;

create table if not exists menu
(
    id int auto_increment
        primary key,
    create_time datetime null,
    update_time datetime null,
    icon varchar(50) null,
    name varchar(50) null,
    parent_id int null,
    priority int null,
    url varchar(255) null
);

create table if not exists user
(
    username varchar(50) not null comment '用户名'
        primary key,
    password varchar(50) not null comment '密码',
    name varchar(50) default '#' null comment '姓名',
    power varchar(2) default '0' not null,
    sex varchar(2) default '#' null comment '性别',
    born datetime null comment '出生日期',
    pic varchar(128) default '/img/user1.jpg' null comment '用户照片',
    contact_number varchar(20) default '#' null comment '联系电话',
    email varchar(20) default '#' null comment '邮箱',
    address varchar(80) default '#' null comment '家庭住址',
    regist_data datetime null comment '注册时间',
    opr_date datetime null comment '操作时间'
);

create table if not exists userbudget
(
    id int auto_increment
        primary key,
    username varchar(50) not null,
    m_inc_total_price double(10,2) null comment '月收入预算金额',
    m_exp_max_price double(10,2) null comment '月最大支出金额',
    m_exp_suit_price double(10,2) null comment '月合理支出金额',
    m_exp_joy_price double(10,2) null comment '月娱乐支出金额',
    m_exp_shop_price double(10,2) null comment '月购物支出金额'
);

/*管理员和普通用户的插入语句  admin为管理员*/
INSERT INTO `financialsys`.`user` (`username`, `password`, `name`, `power`, `sex`, `born`, `pic`, `contact_number`, `email`, `address`, `regist_data`, `opr_date`) VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', '无名之辈', '1', '#', '1998-09-20 09:57:33', '/img/user1.jpg', '15605938553', '13123565@qq.com', '\r\n江滨中大道116号君临闽江6号楼19层', '2019-11-19 18:33:12', NULL);
INSERT INTO `financialsys`.`user` (`username`, `password`, `name`, `power`, `sex`, `born`, `pic`, `contact_number`, `email`, `address`, `regist_data`, `opr_date`) VALUES ('baobao', '7337e2f117b38edd90ef8ddd50c31406', '蔡包包', '0', '1', '1999-03-11 00:00:00', '/img/user3.jpg', '13107650725', '13130735@baobao.com', '江滨中大道116号君临闽江6号楼19层', '2019-11-19 18:33:09', '2019-12-09 09:53:57');

/*菜单插入语句*/
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('1', '2019-12-09 09:23:50', '2019-12-09 09:23:50', 'icon-apple', '主页面', '0', '0', '#');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('2', '2019-11-19 16:41:50', '2019-12-05 16:41:52', '', '界面信息', '1', '1', '\'/pages/usercontext.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('3', '2019-11-19 16:41:56', '2019-11-19 16:41:58', '', '我的信息', '1', '19', '\'/pages/perInfo.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('4', '2019-11-19 16:42:01', '2019-11-19 16:42:04', 'fa-plus-square', '收入', '0', '0', '#');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('5', '2019-11-19 16:42:11', '2019-11-19 16:42:09', '', '收入管理', '1', '4', '\'/pages/income.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('6', '2019-11-19 16:42:13', '2019-11-19 16:42:15', '', '收入统计', '1', '17', '\'/pages/statistics/incstatiscs.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('7', '2019-11-19 16:42:17', '2019-11-19 16:42:19', '', '收入类别', '1', '20', '\'/pages/incometype.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('8', '2019-11-19 16:42:21', '2019-11-19 16:42:23', 'fa-minus-square', '支出', '0', '0', '#');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('9', '2019-11-19 16:42:25', '2019-11-19 16:42:26', '', '支出管理', '1', '8', '\'/pages/expend.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('10', '2019-11-19 16:42:28', '2019-11-19 16:42:29', '', '支出统计', '1', '18', '\'/pages/statistics/expstatiscs.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('11', '2019-11-19 16:42:33', '2019-11-19 16:42:31', '', '支出类别', '1', '21', '\'/pages/expendtype.html\'');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('17', '2019-11-22 17:11:13', '2019-11-22 17:11:13', 'icon-bar-chart', '收入统计管理', '0', '0', '#');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('18', '2019-12-10 08:43:31', '2019-12-10 08:43:35', 'icon-bar-chart', '支出统计管理', '0', '0', '#');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('19', '2019-12-09 09:24:15', '2019-12-09 09:24:15', 'fa-user-secret', '个人信息', '0', '0', '#');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('20', '2019-12-10 08:45:28', '2019-12-10 08:45:31', 'fa-circle', '收入类别管理', '0', '0', '#');
INSERT INTO `financialsys`.`menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES ('21', '2019-12-10 08:46:10', '2019-12-10 08:46:13', 'fa-circle', '支出类别管理', '0', '0', '#');

/*数字字典数据*/
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('1', 'sex', '性别', '0', '男', '', '2019-09-10 14:52:29');
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('2', 'sex', '性别', '1', '女', NULL, '2019-09-10 14:52:47');
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('3', 'pay_method', '支付方式', '0', '微信', NULL, '2019-11-10 22:39:00');
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('4', 'pay_method', '支付方式', '1', '支付宝', NULL, '2019-11-10 22:39:33');
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('5', 'pay_method', '支付方式', '2', '银行转账', NULL, '2019-11-10 22:39:50');
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('6', 'pay_method', '支付方式', '3', '现金', NULL, '2019-11-10 22:40:39');
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('7', 'parent_id', '是否为上级菜单', '1', '是', NULL, '2019-11-21 09:41:36');
INSERT INTO `financialsys`.`dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES ('8', 'parent_id', '是否为上级菜单', '0', '否', NULL, '2019-11-21 09:42:07');

/*收入类别表数据*/
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('1', '生活费');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('2', '兼职收入');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('3', '红包收入');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('4', '以外来钱');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('5', '利息收入');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('6', '理财收入');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('7', '奖学金');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('8', '助学贷款');
INSERT INTO `financialsys`.`incometype` (`income_type_id`, `income_type`) VALUES ('9', '工作收入');

/*支出类别表数据*/
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('1', '饮食');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('2', '服饰美容');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('3', '生活日用');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('4', '文教娱乐');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('5', '交通出行');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('6', '住房缴费');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('7', '通讯物流');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('8', '运动健康');
INSERT INTO `financialsys`.`expendtype` (`expend_type_id`, `enpend_type`) VALUES ('9', '其他消费');



