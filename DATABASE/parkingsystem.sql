-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 07, 2021 lúc 12:44 PM
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
  `username` varchar(25) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `role` varchar(2) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `role`, `status`) VALUES
(1, 'admin', '123456789', 'ad', 1),
(2, 'employee', '123456789', 'ep', 1);

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
(9, '123456789', 'Motorbike', '0', 0, '02-11-2021 15:00:27', '02-11-2021 16:13:40', '73', 'Free', 0),
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
(35, '30h33558', 'Car', '4-8', 1, '06-11-2021 16:07:04', '', '', '', 1),
(36, '264255514', 'Bicycles', '0', 0, '07-11-2021 10:57:02', '', '', '', 1),
(37, '132153147', 'Motorbike', '0', 0, '07-11-2021 10:57:12', '', '', '', 1),
(38, '63191551', 'Car', '30+', 0, '07-11-2021 10:57:21', '', '', '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ticket`
--

CREATE TABLE `ticket` (
  `license_plate` varchar(10) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `expired_date` char(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `ticket`
--

INSERT INTO `ticket` (`license_plate`, `expired_date`, `status`) VALUES
('123456789', '11-11-2021 15:41:00', 1),
('30h33558', '31-12-2021 21:53:30', 1);

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
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `parking`
--
ALTER TABLE `parking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
