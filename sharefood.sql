-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2020-06-28 16:55:44
-- 服务器版本： 5.7.26-log
-- PHP Version: 5.4.45

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sharefood`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `dictionary`
--

INSERT INTO `dictionary` (`id`, `dcis_code`, `dcis_name`, `key_value`, `key_name`, `remarks`, `opr_time`) VALUES
(1, 'sex', '性别', '1', '男', '', '2019-09-10 14:52:29'),
(2, 'sex', '性别', '0', '女', NULL, '2019-09-10 14:52:47'),
(3, 'type', '美食类别', '0', '传统', NULL, '2019-12-26 15:50:14'),
(4, 'type', '美食类别', '1', '儿童', NULL, '2019-12-26 15:50:34'),
(5, 'type', '美食类别', '2', '海鲜', NULL, '2019-12-26 15:50:55'),
(6, 'discount', '优惠活动', '0', '否', NULL, '2019-12-26 15:52:09'),
(7, 'discount', '优惠活动', '1', '是', NULL, '2019-12-26 15:52:11'),
(8, 'crowl', '用户人群', '0', '儿童', NULL, '2019-12-26 15:53:56'),
(9, 'crowl', '用户人群', '1', '成人', NULL, '2019-12-26 15:53:58'),
(10, 'crowl', '用户人群', '2', '老年人', NULL, '2019-12-26 15:54:00'),
(11, 'crowl', '用户人群', '3', '所有人', NULL, '2019-12-26 15:54:03'),
(12, 'fb', '是否发布', '0', '未发布', NULL, '2019-12-26 17:04:16'),
(13, 'fb', '是否发布', '1', '已发布', NULL, '2019-12-26 17:04:36');

-- --------------------------------------------------------

--
-- 表的结构 `t_admin`
--

CREATE TABLE IF NOT EXISTS `t_admin` (
  `id` int(11) NOT NULL COMMENT '主键',
  `username` varchar(100) DEFAULT NULL COMMENT '超级管理员账号',
  `password` varchar(100) DEFAULT NULL COMMENT '超级管理员密码'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='超级管理员';

--
-- 转存表中的数据 `t_admin`
--

INSERT INTO `t_admin` (`id`, `username`, `password`) VALUES
(1, 'admin', '123456');

-- --------------------------------------------------------

--
-- 表的结构 `t_customer`
--

CREATE TABLE IF NOT EXISTS `t_customer` (
  `id` int(11) NOT NULL COMMENT '主键',
  `username` varchar(100) DEFAULT NULL COMMENT '账号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `sex` varchar(100) DEFAULT NULL COMMENT '性别',
  `age` varchar(100) DEFAULT NULL COMMENT '年龄',
  `address` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `idcard` varchar(100) DEFAULT NULL COMMENT '身份证',
  `insertDate` datetime DEFAULT NULL COMMENT '入库日期',
  `headPic` varchar(100) DEFAULT '''/images/users/4.jpg''' COMMENT '头像',
  `level` varchar(100) DEFAULT NULL COMMENT '层级',
  `isft` varchar(100) DEFAULT NULL COMMENT '发帖权限',
  `ispl` varchar(100) DEFAULT NULL COMMENT '评论权限',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';

--
-- 转存表中的数据 `t_customer`
--

INSERT INTO `t_customer` (`id`, `username`, `password`, `name`, `phone`, `sex`, `age`, `address`, `idcard`, `insertDate`, `headPic`, `level`, `isft`, `ispl`, `email`) VALUES
(1, 'baobao', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, NULL, NULL, '2020-01-08 14:44:14', '/images/users/4.jpg', NULL, NULL, NULL, '1159403411@qq.com');

-- --------------------------------------------------------

--
-- 表的结构 `t_like`
--

CREATE TABLE IF NOT EXISTS `t_like` (
  `id` int(11) NOT NULL COMMENT '主键',
  `msId` int(11) DEFAULT NULL COMMENT '美食编号',
  `isLike` varchar(100) DEFAULT NULL COMMENT '喜欢人数',
  `isStart` varchar(100) DEFAULT NULL COMMENT '收藏人数',
  `isView` int(11) DEFAULT NULL COMMENT '浏览人数'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='点赞';

--
-- 转存表中的数据 `t_like`
--

INSERT INTO `t_like` (`id`, `msId`, `isLike`, `isStart`, `isView`) VALUES
(1, 3, '1', NULL, 5),
(2, 4, NULL, NULL, 1),
(3, 10, NULL, NULL, 2);

-- --------------------------------------------------------

--
-- 表的结构 `t_ms`
--

CREATE TABLE IF NOT EXISTS `t_ms` (
  `id` int(11) NOT NULL COMMENT '主键',
  `types` varchar(100) DEFAULT NULL COMMENT '分类',
  `msName` varchar(100) DEFAULT NULL COMMENT '名称',
  `content` varchar(1024) DEFAULT NULL COMMENT '描述信息',
  `msPic` varchar(128) DEFAULT NULL COMMENT '美食图片',
  `insertDate` datetime DEFAULT NULL COMMENT '日期',
  `sendUser` varchar(100) DEFAULT 'admin' COMMENT '发布用户',
  `msTag` varchar(100) DEFAULT NULL COMMENT '美食标签',
  `msAddress` varchar(100) DEFAULT NULL COMMENT '美食地址',
  `isfb` varchar(100) DEFAULT NULL COMMENT '是否发布 0 未发布 1 发布',
  `isDiscount` varchar(50) DEFAULT NULL COMMENT '''0 无优惠活动 1 有优惠活动''',
  `discountSTime` datetime DEFAULT NULL COMMENT '美食优惠开始时间',
  `discountETime` datetime DEFAULT NULL COMMENT '美食优惠结束时间',
  `msNumber` varchar(100) DEFAULT NULL COMMENT '美食联系电话',
  `recommendCrowd` varchar(50) DEFAULT NULL COMMENT '美食推荐人群'
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='美食';

--
-- 转存表中的数据 `t_ms`
--

INSERT INTO `t_ms` (`id`, `types`, `msName`, `content`, `msPic`, `insertDate`, `sendUser`, `msTag`, `msAddress`, `isfb`, `isDiscount`, `discountSTime`, `discountETime`, `msNumber`, `recommendCrowd`) VALUES
(2, '0', '沙拉', '蔬菜沙拉是一道 以圆白菜、番茄、黄瓜等作为主要食材制作而成的美食。蔬菜沙拉是一种非常营养健康的饮食方法。首先它大多不必加热，这样会最大限度的保持住蔬菜中的各种营养不至于被破坏或流失。做水果沙拉时，可在普通的蛋黄沙拉酱内加入适量的甜味鲜奶油，其制出的沙拉奶香味浓郁，甜味加重。水果的种类数量可随个人口味随意增减，同时水果沙拉原料要选新鲜水果，水果切后装盘，摆放时间不宜过长，否则会影响其美观，也会使水果的营养降低。', '/images/page2_img1.jpg', '2019-12-28 23:37:26', 'admin', '1', '', '0', '0', NULL, NULL, '', '0'),
(3, '0', '乌冬面', '乌冬面是最具日本特色的面条之一，与日本的荞麦面、绿茶面并称日本三大面条，是日本料理店 [1]  不可或缺的主角。其口感介于切面和米粉之间，口感偏软，再配上精心调制的汤料，就成了一道可口的面食。是将盐和水混入面粉中制作成的白色较粗（直径4毫米～6毫米）的面条。冬天加入热汤、夏天则放凉食用。凉乌冬面可以蘸被叫做“面佐料汁”的浓料汁食用。', '/images/page2_img2.jpg', '2019-12-28 23:42:16', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0'),
(4, '1', '水果沙拉', '蔬菜沙拉是一道 以圆白菜、番茄、黄瓜等作为主要食材制作而成的美食。蔬菜沙拉是一种非常营养健康的饮食方法。首先它大多不必加热，这样会最大限度的保持住蔬菜中的各种营养不至于被破坏或流失。做水果沙拉时，可在普通的蛋黄沙拉酱内加入适量的甜味鲜奶油，其制出的沙拉奶香味浓郁，甜味加重。水果的种类数量可随个人口味随意增减，同时水果沙拉原料要选新鲜水果，水果切后装盘，摆放时间不宜过长，否则会影响其美观，也会使水果的营养降低。', '/images/page2_img3.jpg', '2019-12-28 23:43:12', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0'),
(5, '1', '金菇炒蛋', '金针菇含有人体必需的8种氨基酸，能降低胆固醇，有预防高血压和心肌梗塞、治疗肝病及肠胃道溃疡病等功能；尤其适合气血不足、营养不良的老人和儿童食用。', '/images/page2_img4.jpg', '2019-12-28 23:45:39', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0'),
(6, '1', '凉拌橘皮', '.橘子皮洗干净,把里面的白瓤剥离干净,橘子皮切成碎末即可,加入各种调料腌制十分钟就可以了。各种调料都切末。', '/images/page2_img5.jpg', '2019-12-28 23:46:00', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0'),
(7, '1', '清江生菜', '蔬菜沙拉是一道 以圆白菜、番茄、黄瓜等作为主要食材制作而成的美食。蔬菜沙拉是一种非常营养健康的饮食方法。首先它大多不必加热，这样会最大限度的保持住蔬菜中的各种营养不至于被破坏或流失。做水果沙拉时，可在普通的蛋黄沙拉酱内加入适量的甜味鲜奶油，其制出的沙拉奶香味浓郁，甜味加重。水果的种类数量可随个人口味随意增减，同时水果沙拉原料要选新鲜水果，水果切后装盘，摆放时间不宜过长，否则会影响其美观，也会使水果的营养降低。', '/images/page2_img6.jpg', '2019-12-28 23:47:35', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0'),
(8, '2', '马卡龙', '马卡龙，又称作玛卡龙、法式小圆饼，是一种用蛋白、杏仁粉、白砂糖和糖霜制作，并夹有水果酱或奶油的法式甜点。口感丰富，外脆内柔，外观五彩缤纷，精致小巧。', '/images/page2_img7.jpg', '2019-12-28 23:49:32', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0'),
(9, '2', '草莓蛋糕', '草莓它是一款受欢迎程度极高的水果，长得形状又萌又可爱，颜色还总是娇艳欲滴。用草莓做各种蛋糕，草莓也总能与它们完美融合，真是美貌与口感俱佳，草莓与蛋糕相结合的可口食物。', '/images/page2_img8.jpg', '2019-12-28 23:50:03', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0'),
(10, '2', '乌龙茶', '乌龙茶（oolong tea），亦称青茶、半发酵茶及全发酵茶，品种较多，是中国几大茶类中，独具鲜明中国特色的茶叶品类。乌龙茶是经过采摘、萎凋、摇青、炒青、揉捻、烘焙等工序后制出的品质优异的茶类。乌龙茶由宋代贡茶龙团、凤饼演变而来，创制于1725年（清雍正年间）前后。', '/images/page2_img9.jpg', '2019-12-28 23:51:24', 'admin', '1', '', '1', '0', NULL, NULL, NULL, '0');

-- --------------------------------------------------------

--
-- 表的结构 `t_pinglun`
--

CREATE TABLE IF NOT EXISTS `t_pinglun` (
  `id` int(11) NOT NULL COMMENT '主键',
  `msId` int(11) DEFAULT NULL COMMENT '美食编号',
  `ownerId` varchar(32) NOT NULL COMMENT '被评论者id，可以是人、项目、资源',
  `fromId` varchar(32) NOT NULL COMMENT '评论者id',
  `fromName` varchar(32) NOT NULL COMMENT '评论者名字',
  `fromAvatar` varchar(512) DEFAULT '' COMMENT '评论者头像',
  `content` varchar(100) DEFAULT NULL COMMENT '评论内容',
  `insertDate` datetime DEFAULT NULL COMMENT '评论日期'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='评论主表';

--
-- 转存表中的数据 `t_pinglun`
--

INSERT INTO `t_pinglun` (`id`, `msId`, `ownerId`, `fromId`, `fromName`, `fromAvatar`, `content`, `insertDate`) VALUES
(1, 4, '0', '1', 'baobao', '', '我喜欢吃这个', '2020-01-08 14:44:46');

-- --------------------------------------------------------

--
-- 表的结构 `t_tag`
--

CREATE TABLE IF NOT EXISTS `t_tag` (
  `id` int(11) NOT NULL COMMENT '主键',
  `tag` varchar(100) DEFAULT NULL COMMENT '标签'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签';

-- --------------------------------------------------------

--
-- 表的结构 `t_tj`
--

CREATE TABLE IF NOT EXISTS `t_tj` (
  `id` int(11) NOT NULL COMMENT '主键',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `pic` varchar(100) DEFAULT NULL COMMENT '图片',
  `tjType` varchar(100) DEFAULT NULL COMMENT '图片类别',
  `tjDesc` varchar(100) DEFAULT NULL COMMENT '图片描述'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='图鉴';

--
-- 转存表中的数据 `t_tj`
--

INSERT INTO `t_tj` (`id`, `title`, `pic`, `tjType`, `tjDesc`) VALUES
(1, '传统美食', '/images/page1_img1.jpg', '0', '传统美食是我国之国粹，你喜欢的话，可以带走'),
(2, '儿童美食', '/images/page1_img2.jpg', '1', '推荐给儿童的美食，家里有宝贝的可以参考一下哦'),
(3, '海鲜美食', '/images/page1_img3.jpg', '2', '海鲜大餐的首选地');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dictionary`
--
ALTER TABLE `dictionary`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_admin`
--
ALTER TABLE `t_admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_customer`
--
ALTER TABLE `t_customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_like`
--
ALTER TABLE `t_like`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_ms`
--
ALTER TABLE `t_ms`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_pinglun`
--
ALTER TABLE `t_pinglun`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_tag`
--
ALTER TABLE `t_tag`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_tj`
--
ALTER TABLE `t_tj`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dictionary`
--
ALTER TABLE `dictionary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `t_admin`
--
ALTER TABLE `t_admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `t_customer`
--
ALTER TABLE `t_customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `t_like`
--
ALTER TABLE `t_like`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `t_ms`
--
ALTER TABLE `t_ms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `t_pinglun`
--
ALTER TABLE `t_pinglun`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `t_tag`
--
ALTER TABLE `t_tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键';
--
-- AUTO_INCREMENT for table `t_tj`
--
ALTER TABLE `t_tj`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
