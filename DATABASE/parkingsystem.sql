-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 22, 2021 lúc 05:56 AM
-- Phiên bản máy phục vụ: 10.4.18-MariaDB
-- Phiên bản PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `parkingsystem`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `id` tinyint(4) NOT NULL,
  `username` char(25) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `password` char(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `role` char(2) COLLATE utf8mb4_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `role`) VALUES
(2, 'employee', '123456789', 'ep'),
(5, 'employee2', '12rgdfhh', 'ep'),
(6, 'employee3', '123456789', 'ep'),
(7, 'employee5', 'dgdf1gffgdf', 'ep'),
(8, 'employee1', '16468464', 'ep'),
(9, 'employee4', 'gfdgdg353', 'ep'),
(11, 'admin', '123456789', 'ad'),
(12, 'admin1', '13213116854', 'ad');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `parking`
--

CREATE TABLE `parking` (
  `id` int(11) NOT NULL,
  `license_plate` char(10) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `type` char(9) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `seat` char(4) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `ticket` tinyint(4) NOT NULL DEFAULT 0,
  `time_in` char(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `time_out` char(20) COLLATE utf8mb4_vietnamese_ci NOT NULL DEFAULT '',
  `parking_time` char(10) COLLATE utf8mb4_vietnamese_ci NOT NULL DEFAULT '',
  `fee` char(12) COLLATE utf8mb4_vietnamese_ci NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `parking`
--

INSERT INTO `parking` (`id`, `license_plate`, `type`, `seat`, `ticket`, `time_in`, `time_out`, `parking_time`, `fee`, `status`) VALUES
(1, '123456789', 'Car', '30+', 0, '01-11-2021 0:21:15', '01-11-2021 16:11:46', '950', '317300', 0),
(2, '1234567890', 'Car', '30+', 0, '01-11-2021 15:10:25', '01-11-2021 16:11:52', '61', '50874', 0),
(3, '4669644135', 'Bicycles', '0', 0, '01-11-2021 15:07:20', '01-11-2021 16:10:49', '63', '3150', 0),
(4, '4669644135', 'Bicycles', '0', 0, '01-11-2021 14:11:17', '01-11-2021 16:11:39', '120', '6000', 0),
(5, '6030215369', 'Bicycles', '0', 0, '01-11-2021 16:12:56', '01-11-2021 16:13:04', '0', '0', 0),
(6, '30h33558', 'Car', '4-8', 0, '01-11-2021 10:28:43', '01-11-2021 17:29:07', '420', '70140', 0),
(7, '4927561948', 'Bicycles', '0', 0, '02-11-2021 14:53:36', '02-11-2021 16:14:13', '80', '4000', 0),
(8, '30h33558', 'Car', '4-8', 0, '02-11-2021 15:00:16', '02-11-2021 16:13:48', '73', '30441', 0),
(9, '123456789', 'Motorbike', '0', 0, '02-11-2021 15:00:27', '02-11-2021 16:13:40', '73', '0', 0),
(10, '0355469274', 'Car', '30+', 0, '02-11-2021 16:14:24', '02-11-2021 16:51:09', '36', '30024', 0),
(11, '30y64065', 'Motorbike', '0', 0, '02-10-2021 16:50:29', '02-11-2021 16:51:18', '44640', '848160', 0),
(12, '1612043809', 'Bicycles', '0', 0, '03-11-2021 15:30:12', '', '', '', 1),
(13, '1213548434', 'Motorbike', '0', 0, '03-11-2021 15:30:20', '04-11-2021 11:04:49', '1174', '22306', 0),
(14, '4651468654', 'Car', '4-8', 0, '03-11-2021 15:30:34', '', '', '', 1),
(15, '4631491693', 'Car', '30+', 0, '03-11-2021 15:30:41', '05-11-2021 13:18:49', '2748', '917832', 0),
(16, '2905597875', 'Car', '9-29', 0, '03-11-2021 15:30:45', '04-11-2021 11:05:09', '1174', '293500', 0),
(17, '4916987076', 'Bicycles', '0', 0, '04-11-2021 11:01:08', '', '', '', 1),
(18, '30H33558', 'Car', '30+', 0, '04-11-2021 11:02:14', '04-11-2021 11:08:03', '5', '4170', 0),
(19, '30h335585', 'Car', '4-8', 0, '04-11-2021 11:02:35', '', '', '', 1),
(20, '012153654', 'Car', '30+', 0, '04-11-2021 11:12:46', '', '', '', 1),
(21, 'rgdfhhdh', 'Motorbike', '0', 0, '04-11-2021 11:12:51', '04-11-2021 14:22:52', '190', '19000', 0),
(22, '4122727589', 'Bicycles', '0', 0, '04-11-2021 11:12:55', '05-11-2021 13:18:32', '1565', '17215', 0),
(23, 'dh13dfgh13', 'Car', '9-29', 0, '04-11-2021 14:22:14', '06-11-2021 16:07:50', '2985', '623865', 0),
(24, '393204097', 'Bicycles', '0', 0, '04-11-2021 14:22:18', '', '', '', 1),
(25, 'fdg1g153', 'Motorbike', '0', 0, '04-11-2021 14:22:22', '05-11-2021 13:20:00', '1377', '26163', 0),
(26, '424439214', 'Bicycles', '0', 0, '05-11-2021 12:09:26', '', '', '', 1),
(27, '12cxgb42gn', 'Car', '30+', 0, '05-11-2021 12:09:26', '', '', '', 1),
(28, 'ggdf15231g', 'Motorbike', '0', 0, '05-11-2021 12:09:42', '', '', '', 1),
(29, '1561561565', 'Motorbike', '0', 0, '05-11-2021 13:17:58', '05-11-2021 13:21:52', '3', '300', 0),
(30, '841532826', 'Motorbike', '0', 0, '05-11-2021 13:18:05', '', '', '', 1),
(31, 'fgddfgdgdf', 'Motorbike', '0', 0, '05-11-2021 13:18:14', '', '', '', 1),
(32, '920705973', 'Bicycles', '0', 0, '05-11-2021 13:20:26', '', '', '', 1),
(33, 's3gs2g3sg4', 'Car', '9-29', 0, '05-11-2021 13:24:24', '07-11-2021 10:57:40', '2733', '571197', 0),
(34, '575032184', 'Bicycles', '0', 0, '05-11-2021 13:24:28', '', '', '', 1),
(35, '30h33558', 'Car', '4-8', 1, '06-11-2021 16:07:04', '08-11-2021 11:04:40', '2577', '0', 0),
(36, '264255514', 'Bicycles', '0', 0, '07-11-2021 10:57:02', '', '', '', 1),
(37, '132153147', 'Motorbike', '0', 0, '07-11-2021 10:57:12', '08-11-2021 09:39:18', '1362', '25878', 0),
(38, '63191551', 'Car', '30+', 0, '07-11-2021 10:57:21', '08-11-2021 14:05:32', '1628', '543752', 0),
(39, '384284777', 'Bicycles', '0', 0, '08-11-2021 09:38:26', '', '', '', 1),
(40, '3445315315', 'Motorbike', '0', 0, '08-11-2021 09:38:34', '', '', '', 1),
(41, '2531331415', 'Car', '9-29', 0, '08-11-2021 09:38:45', '15-11-2021 11:35:21', '10196', '2130964', 0),
(42, '121215222', 'Motorbike', '0', 0, '08-11-2021 09:48:27', '', '', '', 1),
(43, '00536153', 'Car', '30+', 0, '08-11-2021 09:50:43', '10-11-2021 14:53:57', '3183', '1063122', 0),
(44, '5315312315', 'Car', '4-8', 0, '08-11-2021 09:55:01', '', '', '', 1),
(45, '879448556', 'Bicycles', '0', 0, '08-11-2021 14:34:34', '', '', '', 1),
(46, '1514536485', 'Car', '30+', 0, '08-11-2021 14:34:43', '', '', '', 1),
(47, 'ewf564frd', 'Motorbike', '0', 0, '10-11-2021 14:53:14', '11-11-2021 10:54:54', '1201', '22819', 0),
(48, '790580598', 'Bicycles', '0', 0, '10-11-2021 14:53:19', '', '', '', 1),
(49, 'f35d1gf153', 'Car', '30+', 0, '10-11-2021 14:53:26', '', '', '', 1),
(50, '210553', 'Car', '9-29', 0, '10-11-2021 14:53:36', '', '', '', 1),
(51, '004791550', 'Bicycles', '0', 0, '10-11-2021 15:15:57', '11-11-2021 15:49:15', '1473', '16203', 0),
(52, '5315315315', 'Motorbike', '0', 1, '11-11-2021 10:53:45', '13-11-2021 10:16:36', '2842', '0', 0),
(53, '684653', 'Car', '4-8', 1, '11-11-2021 10:54:06', '12-11-2021 16:02:14', '1748', '0', 0),
(54, '4541213', 'Car', '9-29', 1, '11-11-2021 15:48:26', '', '', '', 1),
(55, '452s513135', 'Motorbike', '0', 0, '12-11-2021 15:48:10', '', '', '', 1),
(56, '927012544', 'Bicycles', '0', 0, '12-11-2021 15:48:13', '', '', '', 1),
(57, '6746152161', 'Car', '4-8', 0, '12-11-2021 15:48:22', '', '', '', 1),
(58, '4+44+61', 'Car', '9-29', 0, '12-11-2021 15:48:33', '', '', '', 1),
(59, '5311534148', 'Car', '30+', 0, '12-11-2021 15:48:41', '12-11-2021 16:01:58', '13', '10842', 0),
(60, '656025154', 'Bicycles', '0', 0, '12-11-2021 15:48:52', '', '', '', 1),
(61, '312533088', 'Bicycles', '0', 0, '12-11-2021 15:48:55', '', '', '', 1),
(62, '455308386', 'Motorbike', '0', 0, '12-11-2021 15:49:03', '15-11-2021 10:48:45', '4019', '76361', 0),
(63, '565153531', 'Car', '30+', 0, '12-11-2021 15:49:10', '', '', '', 1),
(64, '922419792', 'Bicycles', '0', 0, '12-11-2021 15:49:14', '', '', '', 1),
(65, '5135153655', 'Motorbike', '0', 0, '12-11-2021 15:49:22', '', '', '', 1),
(66, '1654654531', 'Car', '30+', 0, '12-11-2021 15:49:27', '', '', '', 1),
(67, '5411646466', 'Car', '9-29', 0, '12-11-2021 15:49:36', '15-11-2021 11:36:02', '4066', '849794', 0),
(68, '54gh5455', 'Car', '4-8', 0, '12-11-2021 15:49:40', '', '', '', 1),
(69, '305758551', 'Bicycles', '0', 0, '12-11-2021 15:50:02', '', '', '', 1),
(70, '834947267', 'Bicycles', '0', 0, '12-11-2021 15:50:07', '15-11-2021 10:49:04', '4018', '44198', 0),
(71, '6145456153', 'Motorbike', '0', 0, '12-11-2021 15:50:12', '', '', '', 1),
(72, '121323132.', 'Car', '4-8', 0, '12-11-2021 15:50:16', '', '', '', 1),
(73, '131313315', 'Car', '9-29', 0, '12-11-2021 15:50:21', '', '', '', 1),
(74, '2fg313j3g1', 'Car', '30+', 0, '12-11-2021 15:50:26', '16-11-2021 15:31:33', '5741', '1917494', 0),
(75, '352024549', 'Bicycles', '0', 0, '12-11-2021 15:50:35', '', '', '', 1),
(76, '564685', 'Car', '9-29', 0, '12-11-2021 16:00:22', '', '', '', 1),
(77, '3153453531', 'Motorbike', '0', 1, '10-11-2021 10:18:18', '13-11-2021 10:18:51', '4320', '82080', 0),
(78, '123456789', 'Car', '30+', 1, '10-11-2021 10:19:43', '13-11-2021 10:20:43', '4321', '0', 0),
(79, '203766355', 'Bicycles', '0', 0, '15-11-2021 10:47:58', '', '', '', 1),
(80, '32314', 'Motorbike', '0', 0, '15-11-2021 10:48:04', '16-11-2021 15:31:03', '1722', '32718', 0),
(81, '165165', 'Car', '4-8', 0, '15-11-2021 10:48:11', '', '', '', 1),
(82, '315349', 'Car', '9-29', 0, '15-11-2021 10:48:18', '', '', '', 1),
(83, 'yhfj4556', 'Car', '30+', 0, '15-11-2021 10:48:23', '15-11-2021 11:45:41', '57', '47538', 0),
(84, '5665641', 'Motorbike', '0', 0, '15-11-2021 11:33:16', '', '', '', 1),
(85, '3151531313', 'Motorbike', '0', 0, '15-11-2021 11:45:12', '', '', '', 1),
(86, '316721117', 'Bicycles', '0', 0, '15-11-2021 11:45:16', '18-11-2021 11:29:08', '4303', '47333', 0),
(87, '304740530', 'Bicycles', '0', 0, '15-11-2021 11:46:05', '', '', '', 1),
(88, '977174976', 'Bicycles', '0', 0, '16-11-2021 15:31:43', '', '', '', 1),
(89, '4353', 'Motorbike', '0', 0, '16-11-2021 15:31:49', '', '', '', 1),
(90, '213131313', 'Car', '4-8', 0, '16-11-2021 15:31:54', '19-11-2021 13:52:24', '4220', '443100', 0),
(91, '453dh35', 'Car', '9-29', 0, '16-11-2021 15:32:00', '', '', '', 1),
(92, 'dfgdsg3drf', 'Car', '30+', 0, '16-11-2021 15:32:06', '', '', '', 1),
(93, '45685456', 'Motorbike', '0', 0, '18-11-2021 11:27:53', '', '', '', 1),
(94, '549134988', 'Bicycles', '0', 0, '18-11-2021 11:27:57', '', '', '', 1),
(95, 'gfhfhghfgf', 'Car', '4-8', 0, '18-11-2021 11:28:04', '19-11-2021 15:50:45', '1702', '178710', 0),
(96, '54645465', 'Car', '9-29', 0, '18-11-2021 11:28:38', '', '', '', 1),
(97, '4544533', 'Car', '30+', 0, '18-11-2021 11:28:43', '', '', '', 1),
(98, '929916851', 'Bicycles', '0', 0, '19-11-2021 13:50:06', '19-11-2021 14:38:30', '48', '2400', 0),
(99, '676845453', 'Motorbike', '0', 0, '19-11-2021 13:50:11', '', '', '', 1),
(100, '4565445345', 'Car', '4-8', 0, '19-11-2021 13:50:16', '', '', '', 1),
(101, 'h43fgh43', 'Car', '9-29', 0, '19-11-2021 13:50:22', '', '', '', 1),
(102, '4363646', 'Car', '30+', 0, '19-11-2021 13:50:28', '', '', '', 1),
(103, 'dfgdhdhhdh', 'Car', '4-8', 0, '19-11-2021 14:08:31', '', '', '', 1),
(104, '5644561534', 'Motorbike', '0', 0, '19-11-2021 14:10:20', '22-11-2021 11:55:33', '4185', '79515', 0),
(105, 'gsdgsdfgs', 'Motorbike', '0', 0, '19-11-2021 14:12:41', '', '', '', 1),
(106, 'fghfhgh', 'Car', '4-8', 0, '19-11-2021 14:14:33', '', '', '', 1),
(107, 'hfghfhfg', 'Car', '30+', 0, '19-11-2021 14:31:27', '', '', '', 1),
(108, '706881514', 'Bicycles', '0', 0, '19-11-2021 14:37:06', '22-11-2021 11:56:01', '4158', '45738', 0),
(109, 'yutjjyjghj', 'Motorbike', '0', 0, '19-11-2021 14:37:11', '', '', '', 1),
(110, 'fjfgjfjfjf', 'Car', '4-8', 0, '19-11-2021 14:37:15', '', '', '', 1),
(111, 'hjgfhgfjfg', 'Car', '9-29', 0, '19-11-2021 14:37:33', '', '', '', 1),
(112, 'gfhfghg', 'Car', '4-8', 0, '19-11-2021 15:50:28', '', '', '', 1),
(113, '513061709', 'Bicycles', '0', 0, '19-11-2021 15:50:31', '', '', '', 1),
(114, '764926209', 'Bicycles', '0', 0, '21-11-2021 14:14:23', '', '', '', 1),
(115, '5353453', 'Motorbike', '0', 0, '21-11-2021 14:14:36', '', '', '', 1),
(116, '5345453', 'Car', '4-8', 0, '21-11-2021 14:14:40', '22-11-2021 11:56:16', '1301', '217267', 0),
(117, '443465228', 'Bicycles', '0', 0, '22-11-2021 11:54:57', '', '', '', 1),
(118, '6535345333', 'Motorbike', '0', 0, '22-11-2021 11:55:03', '', '', '', 1),
(119, '4253351', 'Car', '4-8', 0, '22-11-2021 11:55:07', '', '', '', 1),
(120, '3421534153', 'Car', '9-29', 0, '22-11-2021 11:55:11', '', '', '', 1),
(121, '531153531', 'Car', '30+', 0, '22-11-2021 11:55:15', '', '', '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `pricevsslots`
--

CREATE TABLE `pricevsslots` (
  `type` char(9) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `m90` int(11) NOT NULL,
  `90t1440` int(11) NOT NULL,
  `1440p` int(11) NOT NULL,
  `m240` int(11) NOT NULL,
  `240t480` int(11) NOT NULL,
  `480p` int(11) NOT NULL,
  `slots` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `pricevsslots`
--

INSERT INTO `pricevsslots` (`type`, `m90`, `90t1440`, `1440p`, `m240`, `240t480`, `480p`, `slots`) VALUES
('30pcar', 834, 334, 334, 0, 0, 0, 20),
('4t8car', 417, 167, 105, 0, 0, 0, 20),
('9t29car', 667, 250, 209, 0, 0, 0, 20),
('bicycles', 0, 0, 0, 50, 9, 11, 70),
('motorbike', 0, 0, 0, 100, 17, 19, 50);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ticket`
--

CREATE TABLE `ticket` (
  `license_plate` char(10) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `expired_date` char(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `ticket`
--

INSERT INTO `ticket` (`license_plate`, `expired_date`, `status`) VALUES
('123456789', '11-01-2023 15:41:00', 1),
('30h33558', '31-12-2021 21:53:30', 1),
('3131534', '11-11-2022 10:53:25', 1),
('3153453531', '11-11-2020 15:47:42', 1),
('4541213', '11-12-2021 15:48:10', 1),
('5315315315', '11-12-2021 10:50:58', 1),
('6151658646', '11-05-2022 10:53:15', 0),
('684653', '11-12-2021 10:48:05', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `pricevsslots`
--
ALTER TABLE `pricevsslots`
  ADD PRIMARY KEY (`type`);

--
-- Chỉ mục cho bảng `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`license_plate`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `account`
--
ALTER TABLE `account`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `parking`
--
ALTER TABLE `parking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=122;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
