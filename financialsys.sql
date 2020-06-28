-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2020-06-28 16:56:53
-- 服务器版本： 5.7.26-log
-- PHP Version: 5.4.45

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `financialsys`
--

-- --------------------------------------------------------

--
-- 表的结构 `bookmark`
--

CREATE TABLE IF NOT EXISTS `bookmark` (
  `id` int(11) NOT NULL,
  `opr_time` datetime DEFAULT NULL COMMENT '操作时间',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `title` varchar(255) DEFAULT NULL COMMENT '书签内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `dictionary`
--

CREATE TABLE IF NOT EXISTS `dictionary` (
  `id` int(11) NOT NULL,
  `dcis_code` varchar(255) DEFAULT NULL,
  `dcis_name` varchar(255) DEFAULT NULL,
  `key_value` varchar(255) DEFAULT NULL,
  `key_name` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `opr_time` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `dictionary`
--

INSERT INTO `dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES
(1, 'sex', '性别', '0', '男', '', '2019-09-10 14:52:29'),
(2, 'sex', '性别', '1', '女', NULL, '2019-09-10 14:52:47'),
(3, 'pay_method', '支付方式', '0', '微信', NULL, '2019-11-10 22:39:00'),
(4, 'pay_method', '支付方式', '1', '支付宝', NULL, '2019-11-10 22:39:33'),
(5, 'pay_method', '支付方式', '2', '银行转账', NULL, '2019-11-10 22:39:50'),
(6, 'pay_method', '支付方式', '3', '现金', NULL, '2019-11-10 22:40:39'),
(7, 'parent_id', '是否为上级菜单', '1', '是', NULL, '2019-11-21 09:41:36'),
(8, 'parent_id', '是否为上级菜单', '0', '否', NULL, '2019-11-21 09:42:07');

-- --------------------------------------------------------

--
-- 表的结构 `expend`
--

CREATE TABLE IF NOT EXISTS `expend` (
  `expend_id` int(11) NOT NULL,
  `expend_type_id` int(11) DEFAULT NULL COMMENT '支出类型编码',
  `expend_use` varchar(50) DEFAULT NULL COMMENT '支出方式',
  `pay_method` varchar(2) DEFAULT NULL COMMENT '付款方式',
  `pay_account` varchar(20) DEFAULT NULL COMMENT '付款账号',
  `expend_price` double DEFAULT NULL COMMENT '付款金额',
  `expend_date` datetime DEFAULT NULL COMMENT '付款日期',
  `expend_user` varchar(50) DEFAULT NULL COMMENT '支出用户',
  `expend_desc` varchar(128) DEFAULT NULL COMMENT '支出描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `expendtype`
--

CREATE TABLE IF NOT EXISTS `expendtype` (
  `expend_type_id` int(11) NOT NULL COMMENT '支出类别编码',
  `enpend_type` varchar(50) DEFAULT NULL COMMENT '支出类别名称'
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `expendtype`
--

INSERT INTO `expendtype` (`expend_type_id`, `enpend_type`) VALUES
(1, '饮食'),
(2, '服饰美容'),
(3, '生活日用'),
(4, '文教娱乐'),
(5, '交通出行'),
(6, '住房缴费'),
(7, '通讯物流'),
(8, '运动健康'),
(9, '其他消费');

-- --------------------------------------------------------

--
-- 表的结构 `income`
--

CREATE TABLE IF NOT EXISTS `income` (
  `income_id` int(11) NOT NULL,
  `income_type_id` int(11) DEFAULT NULL COMMENT '收入类别编码',
  `income_source` varchar(50) DEFAULT NULL COMMENT '收入来源',
  `pay_method` varchar(2) DEFAULT NULL COMMENT '收款方式',
  `pay_account` varchar(20) DEFAULT NULL COMMENT '收款账号',
  `income_price` double DEFAULT NULL COMMENT '收入金额',
  `income_date` datetime DEFAULT NULL COMMENT '收入日期',
  `income_user` varchar(50) DEFAULT NULL COMMENT '收入用户',
  `income_desc` varchar(128) DEFAULT NULL COMMENT '收入描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `incometype`
--

CREATE TABLE IF NOT EXISTS `incometype` (
  `income_type_id` int(11) NOT NULL COMMENT '收入类别编码',
  `income_type` varchar(50) DEFAULT NULL COMMENT '收入类别名称'
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `incometype`
--

INSERT INTO `incometype` (`income_type_id`, `income_type`) VALUES
(1, '生活费'),
(2, '兼职收入'),
(3, '红包收入'),
(4, '以外来钱'),
(5, '利息收入'),
(6, '理财收入'),
(7, '奖学金'),
(8, '助学贷款'),
(9, '工作收入');

-- --------------------------------------------------------

--
-- 表的结构 `logs`
--

CREATE TABLE IF NOT EXISTS `logs` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `username` varchar(50) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '用户名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `log_desc` varchar(1023) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日志描述',
  `ip_address` varchar(127) COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户ID地址',
  `log_key` varchar(1023) COLLATE utf8mb4_bin DEFAULT '',
  `type` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- 表的结构 `menu`
--

CREATE TABLE IF NOT EXISTS `menu` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `menu`
--

INSERT INTO `menu` (`id`, `create_time`, `update_time`, `icon`, `name`, `parent_id`, `priority`, `url`) VALUES
(1, '2019-12-09 09:23:50', '2019-12-09 09:23:50', 'icon-apple', '主页面', 0, 0, '#'),
(2, '2019-11-19 16:41:50', '2019-12-05 16:41:52', '', '界面信息', 1, 1, '''/pages/usercontext.html'''),
(3, '2019-11-19 16:41:56', '2019-11-19 16:41:58', '', '我的信息', 1, 19, '''/pages/perInfo.html'''),
(4, '2019-11-19 16:42:01', '2019-11-19 16:42:04', 'fa-plus-square', '收入', 0, 0, '#'),
(5, '2019-11-19 16:42:11', '2019-11-19 16:42:09', '', '收入管理', 1, 4, '''/pages/income.html'''),
(6, '2019-11-19 16:42:13', '2019-11-19 16:42:15', '', '收入统计', 1, 17, '''/pages/statistics/incstatiscs.html'''),
(7, '2019-11-19 16:42:17', '2019-11-19 16:42:19', '', '收入类别', 1, 20, '''/pages/incometype.html'''),
(8, '2019-11-19 16:42:21', '2019-11-19 16:42:23', 'fa-minus-square', '支出', 0, 0, '#'),
(9, '2019-11-19 16:42:25', '2019-11-19 16:42:26', '', '支出管理', 1, 8, '''/pages/expend.html'''),
(10, '2019-11-19 16:42:28', '2019-11-19 16:42:29', '', '支出统计', 1, 18, '''/pages/statistics/expstatiscs.html'''),
(11, '2019-11-19 16:42:33', '2019-11-19 16:42:31', '', '支出类别', 1, 21, '''/pages/expendtype.html'''),
(17, '2019-11-22 17:11:13', '2019-11-22 17:11:13', 'icon-bar-chart', '收入统计管理', 0, 0, '#'),
(18, '2019-12-10 08:43:31', '2019-12-10 08:43:35', 'icon-bar-chart', '支出统计管理', 0, 0, '#'),
(19, '2019-12-09 09:24:15', '2019-12-09 09:24:15', 'fa-user-secret', '个人信息', 0, 0, '#'),
(20, '2019-12-10 08:45:28', '2019-12-10 08:45:31', 'fa-circle', '收入类别管理', 0, 0, '#'),
(21, '2019-12-10 08:46:10', '2019-12-10 08:46:13', 'fa-circle', '支出类别管理', 0, 0, '#');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT '#' COMMENT '姓名',
  `power` varchar(2) NOT NULL DEFAULT '0',
  `sex` varchar(2) DEFAULT '#' COMMENT '性别',
  `born` datetime DEFAULT NULL COMMENT '出生日期',
  `pic` varchar(128) DEFAULT '/img/user1.jpg' COMMENT '用户照片',
  `contact_number` varchar(20) DEFAULT '#' COMMENT '联系电话',
  `email` varchar(20) DEFAULT '#' COMMENT '邮箱',
  `address` varchar(80) DEFAULT '#' COMMENT '家庭住址',
  `regist_data` datetime DEFAULT NULL COMMENT '注册时间',
  `opr_date` datetime DEFAULT NULL COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`username`, `password`, `name`, `power`, `sex`, `born`, `pic`, `contact_number`, `email`, `address`, `regist_data`, `opr_date`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '无名之辈', '1', '#', '1998-09-20 09:57:33', '/img/user1.jpg', '15605938553', '13123565@qq.com', '\r\n江滨中大道116号君临闽江6号楼19层', '2019-11-19 18:33:12', NULL),
('baobao', '7337e2f117b38edd90ef8ddd50c31406', '蔡包包', '0', '1', '1999-03-11 00:00:00', '/img/user3.jpg', '13107650725', '13130735@baobao.com', '江滨中大道116号君临闽江6号楼19层', '2019-11-19 18:33:09', '2019-12-09 09:53:57');

-- --------------------------------------------------------

--
-- 表的结构 `userbudget`
--

CREATE TABLE IF NOT EXISTS `userbudget` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `m_inc_total_price` double(10,2) DEFAULT NULL COMMENT '月收入预算金额',
  `m_exp_max_price` double(10,2) DEFAULT NULL COMMENT '月最大支出金额',
  `m_exp_suit_price` double(10,2) DEFAULT NULL COMMENT '月合理支出金额',
  `m_exp_joy_price` double(10,2) DEFAULT NULL COMMENT '月娱乐支出金额',
  `m_exp_shop_price` double(10,2) DEFAULT NULL COMMENT '月购物支出金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookmark`
--
ALTER TABLE `bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dictionary`
--
ALTER TABLE `dictionary`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `expend`
--
ALTER TABLE `expend`
  ADD PRIMARY KEY (`expend_id`);

--
-- Indexes for table `expendtype`
--
ALTER TABLE `expendtype`
  ADD PRIMARY KEY (`expend_type_id`);

--
-- Indexes for table `income`
--
ALTER TABLE `income`
  ADD PRIMARY KEY (`income_id`);

--
-- Indexes for table `incometype`
--
ALTER TABLE `incometype`
  ADD PRIMARY KEY (`income_type_id`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `userbudget`
--
ALTER TABLE `userbudget`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookmark`
--
ALTER TABLE `bookmark`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `dictionary`
--
ALTER TABLE `dictionary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `expend`
--
ALTER TABLE `expend`
  MODIFY `expend_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `expendtype`
--
ALTER TABLE `expendtype`
  MODIFY `expend_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '支出类别编码',AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `income_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `incometype`
--
ALTER TABLE `incometype`
  MODIFY `income_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收入类别编码',AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `userbudget`
--
ALTER TABLE `userbudget`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
