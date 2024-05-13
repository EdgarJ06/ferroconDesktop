-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-06-2020 a las 17:35:07
-- Versión del servidor: 10.3.15-MariaDB
-- Versión de PHP: 7.1.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ferreteria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aper_caja`
--

CREATE TABLE `aper_caja` (
  `id_aper_caja` int(11) NOT NULL,
  `id_caja` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `fecha_hora_aper` datetime NOT NULL,
  `monto_ini` double NOT NULL,
  `fecha_hora_cierr` datetime DEFAULT NULL,
  `monto_final` double NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `aper_caja`
--

INSERT INTO `aper_caja` (`id_aper_caja`, `id_caja`, `id_user`, `fecha_hora_aper`, `monto_ini`, `fecha_hora_cierr`, `monto_final`, `estado`) VALUES
(1, 1, 1, '2020-03-13 20:57:35', 200, '2020-03-17 21:01:53', -5304.75, 0),
(2, 2, 1, '2020-03-17 20:19:20', 20, '2020-03-17 21:21:27', -225, 0),
(3, 2, 1, '2020-03-17 21:39:32', 200, '2020-03-17 21:43:09', 0, 0),
(4, 2, 1, '2020-03-17 21:43:34', 200, '2020-03-17 21:45:22', 0, 0),
(5, 1, 1, '2020-03-17 21:45:36', 200, '2020-03-17 21:48:26', 0, 0),
(6, 1, 1, '2020-03-17 21:49:02', 200, NULL, 0, 0),
(7, 1, 1, '2020-03-17 21:49:02', 200, NULL, 0, 0),
(8, 1, 1, '2020-03-17 21:49:02', 200, NULL, 0, 0),
(9, 1, 1, '2020-03-17 21:49:02', 200, '2020-03-17 21:57:43', 0, 0),
(10, 2, 1, '2020-03-17 21:54:53', 52, '2020-03-17 21:57:43', 0, 0),
(11, 1, 1, '2020-03-17 21:58:03', 20, '2020-03-25 18:47:13', 463.42, 0),
(12, 2, 1, '2020-03-25 18:47:42', 90, '2020-03-26 18:54:10', 2248.04, 0),
(13, 1, 1, '2020-03-26 18:24:42', 78, '2020-03-26 18:47:26', 0, 0),
(14, 1, 1, '2020-03-26 18:47:43', 90, '2020-03-26 18:50:22', 0, 0),
(15, 2, 1, '2020-03-26 18:54:39', 98, '2020-06-04 19:27:33', 50759.79, 0),
(16, 2, 1, '2020-06-04 19:38:29', 100, '2020-06-05 18:36:09', 3977.96, 0),
(17, 2, 1, '2020-06-05 20:19:53', 100, '2020-06-06 13:24:30', 173.45, 0),
(18, 2, 1, '2020-06-06 15:55:21', 100, '2020-06-06 16:12:35', 7636, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caja`
--

CREATE TABLE `caja` (
  `id_caja` int(11) NOT NULL,
  `num_caja` int(11) NOT NULL,
  `descrip` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `caja`
--

INSERT INTO `caja` (`id_caja`, `num_caja`, `descrip`) VALUES
(1, 1, 'Caja Chica'),
(2, 2, 'Caja 2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `cod_ciu` int(11) NOT NULL,
  `cod_prov` int(11) DEFAULT NULL,
  `nom_ciu` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`cod_ciu`, `cod_prov`, `nom_ciu`) VALUES
(1, 1, 'Ciudad del Este'),
(2, 3, 'Medellin'),
(3, 2, 'Duran'),
(4, 1, 'Salinas'),
(5, 2, 'Daule'),
(6, 2, 'Guayaquil'),
(7, 4, 'Portoviejo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `cod_cli` int(11) NOT NULL,
  `ced_cli` longtext DEFAULT NULL,
  `nom_cli` longtext DEFAULT NULL,
  `ape_cli` longtext DEFAULT NULL,
  `dir_cli` longtext DEFAULT NULL,
  `telf_cli` longtext DEFAULT NULL,
  `nit_cli` longtext DEFAULT NULL,
  `cod_ciu` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`cod_cli`, `ced_cli`, `nom_cli`, `ape_cli`, `dir_cli`, `telf_cli`, `nit_cli`, `cod_ciu`) VALUES
(1, '0705835338', 'Oscar', 'Egas', 'Paraguay entre colon y buenavista', '0968768476', 'oscaregas@hotmail.com', 5),
(2, '00000999999', 'werqer', 'nnnnn', 'fsdasdfadfasdffsd', '898767898', 'ldsfkalds@hotmail.com', 1),
(3, '07056854', 'Carlos', 'Segarra', 'Av. Los Vergeles y 9 de octubre', '1111111', 'carlsegarra@gmail.com', 5),
(4, '0705835339', 'Jose', 'Brionez', 'Independencia y municipalidad', '6985458', 'josebrionez@hotmail.com', 4),
(5, '0968768457', 'Ricardo', 'Varela', 'Salinas y 9 de agosto', '2548545', 'admin@hotmail.com', 4),
(6, '096874587', 'Alex', 'Briz', '9 de Mayo y Puerto Alegre', '1112555', 'alex@hotmail.com', 7),
(7, '0705435329', 'Cristhian', 'Suarez', 'Av 9 de Octubre y 10 de Agosto', '65895455', 'cristian@gmail.com', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `det_caja`
--

CREATE TABLE `det_caja` (
  `id_det` int(11) NOT NULL,
  `id_per_caja` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `descripcion` text NOT NULL,
  `cod_vntas` varchar(10) DEFAULT NULL,
  `ingreso` double NOT NULL,
  `cod_pedido` varchar(10) DEFAULT NULL,
  `egreso` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `det_caja`
--

INSERT INTO `det_caja` (`id_det`, `id_per_caja`, `fecha`, `descripcion`, `cod_vntas`, `ingreso`, `cod_pedido`, `egreso`) VALUES
(1, 12, '2020-03-26', 'Pedidos de Mercaderiagg', NULL, 0, NULL, 135),
(2, 1, '2020-03-13', 'Pedidos de Mercaderia', NULL, 0, 'P0000003', 90),
(3, 1, '2020-03-13', 'Ventas de Mercaderia', 'F0000001', 45, NULL, 0),
(4, 1, '2020-03-13', 'Ventas de Mercaderia', 'F0000002', 85, NULL, 0),
(5, 1, '2020-03-13', 'Pedidos de Mercaderia', NULL, 0, 'P0000004', 225),
(6, 1, '2020-03-14', 'Ventas de Mercaderia', 'F0000003', 45, NULL, 0),
(7, 1, '2020-03-14', 'Ventas de Mercaderia', 'F0000004', 85, NULL, 0),
(8, 1, '2020-03-14', 'Ventas de Mercaderia', 'F0000005', 85, NULL, 0),
(9, 1, '2020-03-14', 'Ventas de Mercaderia', 'F0000006', 85, NULL, 0),
(10, 1, '2020-03-14', 'Ventas de Mercaderia', 'F0000007', 85, NULL, 0),
(11, 1, '2020-03-14', 'Ventas de Mercaderia', 'F0000008', 90, NULL, 0),
(12, 1, '2020-03-14', 'Ventas de Mercaderia', 'F0000009', 225, NULL, 0),
(13, 1, '2020-03-14', 'Pedidos de Mercaderia', NULL, 0, 'P0000005', 900),
(14, 1, '2020-03-15', 'Pedidos de Mercaderia', NULL, 0, 'P0000006', 180),
(15, 12, '2020-03-26', 'Pedidos de Mercaderia hjhjhjhjjhhjhhj', NULL, 0, NULL, 2025),
(16, 1, '2020-03-17', 'Pedidos de Mercaderia', NULL, 0, 'P0000008', 2691),
(17, 1, '2020-03-17', 'Ventas de Mercaderia', 'F0000010', 201.25, NULL, 0),
(18, 1, '2020-03-17', 'Pedidos de Mercaderia', NULL, 0, 'P0000009', 90),
(19, 2, '2020-03-17', 'Pedidos de Mercaderia', NULL, 0, 'P0000010', 225),
(20, 11, '2020-03-17', 'Ventas de Mercaderia', 'F0000011', 293.25, NULL, 0),
(21, 11, '2020-03-19', 'Ventas de Mercaderia', 'F0000012', 396.75, NULL, 0),
(22, 11, '2020-03-20', 'Pedidos de Mercaderia', NULL, 0, 'P0000011', 180),
(23, 11, '2020-03-20', 'Ventas de Mercaderia', 'F0000013', 396.75, NULL, 0),
(24, 11, '2020-03-20', 'Pago de Crédito', NULL, 0, 'P0000005', 200),
(25, 11, '2020-03-24', 'Pago de Crédito', NULL, 0, 'P0000005', 200),
(26, 11, '2020-03-24', 'Pago de Crédito', NULL, 0, 'P0000006', 43.33),
(27, 12, '2020-03-25', 'Pago a trabajadores  en la empresa', NULL, 0, NULL, 0.98),
(28, 12, '2020-03-25', 'Pago de Almuerzos', NULL, 0, NULL, 0.98),
(29, 12, '2020-03-25', 'pago de planilla de luz', NULL, 0, NULL, 90),
(30, 12, '2020-03-26', 'pago de deudas pendientes', NULL, 0, NULL, 100),
(31, 12, '2020-03-26', 'Ventas de Mercaderia', 'F0000014', 4600, NULL, 0),
(32, 15, '2020-04-07', 'Pedidos de Mercaderia', NULL, 0, 'P0000012', 447.48),
(33, 15, '2020-04-17', 'Pago de Crédito', NULL, 0, 'P0000007', 508.333),
(34, 15, '2020-04-17', 'Ventas de Mercaderia', 'F0000015', 909.44, NULL, 0),
(35, 15, '2020-04-19', 'Pedidos de Mercaderia', NULL, 0, 'P0000013', 74.34),
(36, 15, '2020-05-03', 'Ventas de Mercaderia', 'F0000016', 20001, NULL, 0),
(37, 15, '2020-05-03', 'Cobro de Crédito', 'F0000015', 151.5, NULL, 0),
(38, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000017', 578.2, NULL, 0),
(39, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000018', 1156.4, NULL, 0),
(40, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000019', 1156.4, NULL, 0),
(41, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000020', 501.5, NULL, 0),
(42, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000021', 8000.4, NULL, 0),
(43, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000022', 70.8, NULL, 0),
(44, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000023', 1156.4, NULL, 0),
(45, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000024', 578.2, NULL, 0),
(46, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000025', 70.8, NULL, 0),
(47, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000026', 1156.4, NULL, 0),
(48, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000027', 16000.8, NULL, 0),
(49, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000028', 578.2, NULL, 0),
(50, 15, '2020-05-04', 'Ventas de Mercaderia', 'F0000029', 407.1, NULL, 0),
(51, 15, '2020-06-04', 'Ventas de Mercaderia', 'F0000030', 1120, NULL, 0),
(52, 15, '2020-06-04', 'Pedidos de Mercaderia', NULL, 0, 'P0000014', 1803.6),
(54, 16, '2020-06-04', 'klkjlkj', NULL, 0, NULL, 63),
(55, 16, '2020-06-05', 'Ventas de Mercaderia', 'F0000031', 4040.96, NULL, 0),
(56, 17, '2020-06-06', 'Ventas de Mercaderia', 'F0000032', 73.45, NULL, 0),
(59, 18, '2020-06-06', 'Ventas de Mercaderia', 'F0000034', 414, NULL, 0),
(60, 18, '2020-06-06', 'Pedidos de Mercaderia', NULL, 0, 'P0000016', 575),
(61, 18, '2020-06-06', 'por compra de mercaderia segun factura n 000025', NULL, 0, NULL, 100),
(62, 18, '2020-06-06', 'Ventas de Mercaderia', 'F0000035', 7797, NULL, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `det_pedidos`
--

CREATE TABLE `det_pedidos` (
  `cod_ped` varchar(10) DEFAULT NULL,
  `cod_prod` varchar(10) DEFAULT NULL,
  `cant_ped` int(11) DEFAULT NULL,
  `prec_ped` double DEFAULT NULL,
  `iva_ped` double DEFAULT NULL,
  `importe` double DEFAULT NULL,
  `estado` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `det_pedidos`
--

INSERT INTO `det_pedidos` (`cod_ped`, `cod_prod`, `cant_ped`, `prec_ped`, `iva_ped`, `importe`, `estado`) VALUES
('P0000001', '67703', 2, 45, 0, 90, '1'),
('P0000002', '67703', 3, 45, 0, 135, '1'),
('P0000003', '67703', 2, 45, 0, 90, '1'),
('P0000004', '67703', 5, 45, 0, 225, '1'),
('P0000005', '67703', 20, 45, 0, 900, '1'),
('P0000006', '67703', 4, 45, 0, 180, '1'),
('P0000007', '67703', 45, 45, 0, 2025, '1'),
('P0000008', '67703', 52, 45, 351, 2340, '1'),
('P0000009', '67703', 2, 45, 0, 90, '1'),
('P0000010', '67703', 5, 45, 0, 225, '1'),
('P0000011', '67703', 4, 45, 0, 180, '1'),
('P0000012', '27350', 4, 9, 6.48, 36, '1'),
('P0000012', '67703', 9, 45, 0, 405, '1'),
('P0000013', '27350', 7, 9, 11.34, 63, '1'),
('P0000014', '27350', 45, 9, 48.6, 405, '1'),
('P0000014', '67703', 30, 45, 0, 1350, '1'),
('P0000015', '67703', 100, 45, 450, 4500, '1'),
('P0000016', '90704', 5, 100, 75, 500, '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `det_ventas`
--

CREATE TABLE `det_ventas` (
  `cod_vntas` varchar(10) DEFAULT NULL,
  `cod_prod` varchar(10) DEFAULT NULL,
  `cant` int(11) DEFAULT NULL,
  `prec` double DEFAULT NULL,
  `iva_vntas` double DEFAULT NULL,
  `importe` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `det_ventas`
--

INSERT INTO `det_ventas` (`cod_vntas`, `cod_prod`, `cant`, `prec`, `iva_vntas`, `importe`) VALUES
('F0000001', '67703', 1, 45, 0, 45),
('F0000002', '63569', 1, 85, 0, 85),
('F0000003', '67703', 1, 45, 0, 45),
('F0000004', '63569', 1, 85, 0, 85),
('F0000005', '63569', 1, 85, 0, 85),
('F0000006', '63569', 1, 85, 0, 85),
('F0000007', '63569', 1, 85, 0, 85),
('F0000008', '67703', 2, 45, 0, 90),
('F0000009', '67703', 5, 45, 0, 225),
('F0000010', '67703', 2, 45, 13.5, 90),
('F0000010', '63569', 1, 85, 12.75, 85),
('F0000011', '63569', 3, 85, 38.25, 255),
('F0000012', '67703', 2, 45, 13.5, 90),
('F0000012', '63569', 3, 85, 38.25, 255),
('F0000013', '67703', 2, 45, 13.5, 90),
('F0000013', '63569', 3, 85, 38.25, 255),
('F0000014', '63569', 10, 85, 127.5, 850),
('F0000014', '67703', 70, 45, 472.5, 3150),
('F0000015', '67942', 8, 98, 125.44, 784),
('F0000016', '67703', 25, 678, 3051, 16950),
('F0000017', '67942', 5, 98, 88.2, 490),
('F0000018', '67942', 10, 98, 176.4, 980),
('F0000019', '67942', 10, 98, 176.4, 980),
('F0000020', '63569', 5, 85, 76.5, 425),
('F0000021', '67703', 10, 678, 1220.4, 6780),
('F0000022', '26344', 10, 6, 10.8, 60),
('F0000023', '67942', 10, 98, 176.4, 980),
('F0000024', '67942', 5, 98, 88.2, 490),
('F0000025', '26344', 10, 6, 10.8, 60),
('F0000026', '67942', 10, 98, 176.4, 980),
('F0000027', '67703', 20, 678, 2440.8, 13560),
('F0000028', '67942', 5, 98, 88.2, 490),
('F0000029', '19615', 5, 69, 62.1, 345),
('F0000030', '58781', 10, 100, 120, 1000),
('F0000031', '58781', 2, 100, 24, 200),
('F0000031', '26344', 3, 6, 2.16, 18),
('F0000031', '67703', 5, 678, 406.8, 3390),
('F0000032', '36623', 5, 13, 8.45, 65),
('F0000033', '66585', 5, 30, 15, 150),
('F0000034', '22443', 2, 105, 31.5, 210),
('F0000034', '66585', 5, 30, 22.5, 150),
('F0000035', '67703', 10, 678, 1017, 6780);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `cod_empresa` int(11) NOT NULL,
  `nom_empresa` text NOT NULL,
  `ruc` text NOT NULL,
  `nom_representante` text NOT NULL,
  `telf` varchar(12) NOT NULL,
  `direccion` text NOT NULL,
  `email` text NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`cod_empresa`, `nom_empresa`, `ruc`, `nom_representante`, `telf`, `direccion`, `email`, `estado`) VALUES
(1, 'Ferreteria La Economia', '2025565465', 'Eduardo Briz', '0359855', 'Ecuador Guayas Guayaquil entre 9 de octubre y Vicente Rocafuerte', 'laeconomia@hotmail.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `cod_marca` int(11) NOT NULL,
  `nom_marca` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`cod_marca`, `nom_marca`) VALUES
(1, 'Bellota'),
(2, 'Stanley'),
(3, 'DeWalt'),
(4, 'Bosch'),
(5, 'Viro'),
(6, 'Plastigama'),
(7, 'Pintuco'),
(8, 'Dacon'),
(9, 'Rocafuerte'),
(10, 'Rival');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `cod_pais` int(11) DEFAULT NULL,
  `nom_pais` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`cod_pais`, `nom_pais`) VALUES
(1, 'Ecuador'),
(2, 'Colombia'),
(3, 'Paraguay');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `cod_ped` varchar(10) NOT NULL,
  `cod_prov` int(11) DEFAULT NULL,
  `cod_usu` int(11) DEFAULT NULL,
  `fecha_ped` date DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `iva_ped` double DEFAULT NULL,
  `desc_ped` double DEFAULT NULL,
  `total_ped` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`cod_ped`, `cod_prov`, `cod_usu`, `fecha_ped`, `subtotal`, `iva_ped`, `desc_ped`, `total_ped`) VALUES
('P0000001', 1, 1, '2020-03-13', 90, 0, 0, 90),
('P0000002', 1, 1, '2020-03-13', 135, 0, 0, 135),
('P0000003', 1, 1, '2020-03-13', 90, 0, 0, 90),
('P0000004', 1, 1, '2020-03-13', 225, 0, 0, 225),
('P0000005', 1, 1, '2020-03-14', 900, 0, 0, 900),
('P0000006', 1, 1, '2020-03-15', 180, 0, 0, 180),
('P0000007', 1, 1, '2020-03-17', 2025, 0, 0, 2025),
('P0000008', 1, 1, '2020-03-17', 2340, 351, 0, 2691),
('P0000009', 1, 1, '2020-03-17', 90, 0, 0, 90),
('P0000010', 1, 1, '2020-03-17', 225, 0, 0, 225),
('P0000011', 1, 1, '2020-03-20', 180, 0, 0, 180),
('P0000012', 1, 1, '2020-04-07', 441, 6.48, 0, 447.48),
('P0000013', 1, 1, '2020-04-19', 63, 11.34, 0, 74.34),
('P0000014', 1, 1, '2020-06-04', 1755, 48.6, 0, 1803.6),
('P0000015', 2, 1, '2020-06-06', 4500, 450, 0, 4950),
('P0000016', 3, 1, '2020-06-06', 500, 75, 0, 575);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permisos`
--

CREATE TABLE `permisos` (
  `id_per` int(11) DEFAULT NULL,
  `ced_usu` longtext DEFAULT NULL,
  `per_delete` char(1) DEFAULT NULL,
  `per_add` char(1) DEFAULT NULL,
  `per_update` char(1) DEFAULT NULL,
  `cod_discolor` int(11) DEFAULT NULL,
  `id_ima` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `permisos`
--

INSERT INTO `permisos` (`id_per`, `ced_usu`, `per_delete`, `per_add`, `per_update`, `cod_discolor`, `id_ima`) VALUES
(1, '0705835338', '1', '1', '1', 2, 2),
(2, '0705835336', '0', '0', '1', 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `cod_usu` int(11) NOT NULL,
  `cod_tip_user` int(11) DEFAULT NULL,
  `password` longtext DEFAULT NULL,
  `ced_usu` longtext DEFAULT NULL,
  `nom_usu` longtext DEFAULT NULL,
  `ape_usu` longtext DEFAULT NULL,
  `dir_usu` longtext DEFAULT NULL,
  `telf_usu` longtext DEFAULT NULL,
  `email_usu` longtext DEFAULT NULL,
  `cod_ciu` int(11) DEFAULT NULL,
  `estado` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`cod_usu`, `cod_tip_user`, `password`, `ced_usu`, `nom_usu`, `ape_usu`, `dir_usu`, `telf_usu`, `email_usu`, `cod_ciu`, `estado`) VALUES
(1, 1, '123', '0705835338', 'Administrador', 'Admin', 'Administrador', '1968746', 'admin@hotmail.com', 5, '0'),
(2, 2, '123', '0705835336', 'prueba', 'sistema', 'Las praderas', '03652555', 'prueba@gmail.com', 3, '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `num_prod` int(11) DEFAULT NULL,
  `cod_prod` varchar(10) NOT NULL,
  `cod_prove` int(11) DEFAULT NULL,
  `cod_marca` int(11) DEFAULT NULL,
  `cod_tip` int(11) DEFAULT NULL,
  `prec_compra` double DEFAULT NULL,
  `descuento` double DEFAULT NULL,
  `prec_vnta` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `iva_prod` char(1) DEFAULT NULL,
  `descrip` longtext DEFAULT NULL,
  `observ` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`num_prod`, `cod_prod`, `cod_prove`, `cod_marca`, `cod_tip`, `prec_compra`, `descuento`, `prec_vnta`, `stock`, `iva_prod`, `descrip`, `observ`) VALUES
(4, '19615', 1, 4, 2, 67, 0, 69, 36, '1', 'LLantas para tractor', 'sin novedad'),
(9, '22443', 3, 10, 6, 100, 0, 105, 28, '1', 'Tubos num 40 ', 'ninguna'),
(3, '26344', 1, 7, 1, 9, 0, 6, 75, '1', 'Pinturas de Agua', 'ninguna'),
(7, '36623', 1, 8, 4, 10, 0, 13, 5, '1', 'Cable Numero 10', 'sin novedad'),
(6, '58781', 1, 3, 2, 0, 0, 100, 8, '1', 'Llantas para tractor', 'sin novedad'),
(1, '63569', 1, 1, 1, 0, 0, 85, 20, '1', 'Chapa de puerta electrica ', 'ninguna'),
(8, '66585', 2, 9, 5, 20, 0, 30, 20, '1', '100 fdas de cemento', 'sin observacion'),
(2, '67703', 1, 1, 1, 45, 0, 678, 52, '1', 'afasdfas', 'fasdfad'),
(5, '67942', 1, 7, 1, 898, 0, 98, 27, '1', 'Color rojo y naranja', 'ninguna');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_compras`
--

CREATE TABLE `productos_compras` (
  `num_prod` int(11) DEFAULT NULL,
  `cod_prod` varchar(10) NOT NULL,
  `cod_marca` int(11) DEFAULT NULL,
  `cod_tip` int(11) DEFAULT NULL,
  `prec_compra` double DEFAULT NULL,
  `descuento` double DEFAULT NULL,
  `iva_prod` char(1) DEFAULT NULL,
  `descripcion` longtext DEFAULT NULL,
  `observ` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `productos_compras`
--

INSERT INTO `productos_compras` (`num_prod`, `cod_prod`, `cod_marca`, `cod_tip`, `prec_compra`, `descuento`, `iva_prod`, `descripcion`, `observ`) VALUES
(2, '27350', 5, 1, 9, 0, '1', 'dsfa;skldjfalks', 'fdoaklsdjfa'),
(1, '67703', 1, 1, 45, 0, '0', 'afasdfas', 'fasdfad'),
(3, '90704', 10, 6, 100, 0, '1', 'Tubos numero 5', 'ninguna');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `cod_prove` int(11) NOT NULL,
  `cod_ciu` int(11) DEFAULT NULL,
  `ced_ruc` longtext DEFAULT NULL,
  `nom_prove` longtext DEFAULT NULL,
  `dir_prove` longtext DEFAULT NULL,
  `email_prove` longtext DEFAULT NULL,
  `telf_prove` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`cod_prove`, `cod_ciu`, `ced_ruc`, `nom_prove`, `dir_prove`, `email_prove`, `telf_prove`) VALUES
(1, 2, '0705835338', 'SYSTEM.YA', 'bbbbbbbbbbbb', 'fasdfs@hotmail.com', '12584556'),
(2, 6, '0705835336', 'Cementina cia.', 'Vicente Rocafuerte y 10 de Agosto', 'cementina@gmail.com', '65874585'),
(3, 4, '11112525', 'Plastigama S.A', 'Salinas Av. Las Palmeras entre Junin y 10 de Agosto', 'plastigama@gmail.com', '95865585');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

CREATE TABLE `provincia` (
  `cod_prov` int(11) DEFAULT NULL,
  `cod_pais` int(11) DEFAULT NULL,
  `nom_prov` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provincia`
--

INSERT INTO `provincia` (`cod_prov`, `cod_pais`, `nom_prov`) VALUES
(1, 1, 'Santa Elena'),
(2, 1, 'Guayas'),
(3, 2, 'Antioquia'),
(4, 1, 'Manabi');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbajustes`
--

CREATE TABLE `tbajustes` (
  `idajuste` int(11) NOT NULL,
  `idmotivo` int(11) NOT NULL,
  `codprod` varchar(10) NOT NULL,
  `id_user` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tbajustes`
--

INSERT INTO `tbajustes` (`idajuste`, `idmotivo`, `codprod`, `id_user`, `cantidad`) VALUES
(1, 1, '19615', 1, 90),
(3, 1, '63569', 1, 1),
(4, 1, '19615', 1, 4),
(5, 2, '63569', 1, 5),
(6, 2, '58781', 1, 10),
(7, 2, '66585', 1, 20),
(8, 2, '22443', 1, 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbmotivoajustes`
--

CREATE TABLE `tbmotivoajustes` (
  `idmotivo` int(11) NOT NULL,
  `motivo` text NOT NULL,
  `tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tbmotivoajustes`
--

INSERT INTO `tbmotivoajustes` (`idmotivo`, `motivo`, `tipo`) VALUES
(1, 'Venta de mercaderia', 'Salida'),
(2, 'Compra de mercaderia', 'Entrada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_iva`
--

CREATE TABLE `tb_iva` (
  `cod_iva` int(11) DEFAULT NULL,
  `val_iva` double DEFAULT NULL,
  `estado` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tb_iva`
--

INSERT INTO `tb_iva` (`cod_iva`, `val_iva`, `estado`) VALUES
(1, 0.18, '0'),
(2, 0.16, '0'),
(3, 0.12, '0'),
(4, 0.13, '0'),
(5, 0.1, '0'),
(6, 0.15, '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE `tipo` (
  `id_tipo` int(11) NOT NULL,
  `descrip` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo`
--

INSERT INTO `tipo` (`id_tipo`, `descrip`) VALUES
(1, 'Pinturas'),
(2, 'LLantas'),
(3, 'Pegamento'),
(4, 'Cable electrico'),
(5, 'Cemento'),
(6, 'Tuberias');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tip_user`
--

CREATE TABLE `tip_user` (
  `cod_tip_user` int(11) DEFAULT NULL,
  `descripcion` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tip_user`
--

INSERT INTO `tip_user` (`cod_tip_user`, `descripcion`) VALUES
(1, 'Administrador'),
(2, 'Vendedor'),
(3, 'otros');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `cod_vntas` varchar(10) NOT NULL,
  `cod_cli` int(11) DEFAULT NULL,
  `cod_usu` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `sub_total` double DEFAULT NULL,
  `iva_vntas` double DEFAULT NULL,
  `descuento` double DEFAULT NULL,
  `total_vntas` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`cod_vntas`, `cod_cli`, `cod_usu`, `date`, `sub_total`, `iva_vntas`, `descuento`, `total_vntas`) VALUES
('F0000001', 1, 1, '2020-03-13 00:00:00', 45, 0, 0, 45),
('F0000002', 1, 1, '2020-03-13 00:00:00', 85, 0, 0, 85),
('F0000003', 1, 1, '2020-03-14 00:00:00', 45, 0, 0, 45),
('F0000004', 1, 1, '2020-03-14 00:00:00', 85, 0, 0, 85),
('F0000005', 1, 1, '2020-03-14 00:00:00', 85, 0, 0, 85),
('F0000006', 1, 1, '2020-03-14 00:00:00', 85, 0, 0, 85),
('F0000007', 1, 1, '2020-03-14 00:00:00', 85, 0, 0, 85),
('F0000008', 1, 1, '2020-03-14 00:00:00', 90, 0, 0, 90),
('F0000009', 1, 1, '2020-03-14 00:00:00', 225, 0, 0, 225),
('F0000010', 1, 1, '2020-03-17 00:00:00', 175, 26.25, 0, 201.25),
('F0000011', 1, 1, '2020-03-17 00:00:00', 255, 38.25, 0, 293.25),
('F0000012', 1, 1, '2020-03-19 00:00:00', 345, 51.75, 0, 396.75),
('F0000013', 1, 1, '2020-03-20 00:00:00', 345, 51.75, 0, 396.75),
('F0000014', 1, 1, '2020-03-26 00:00:00', 4000, 600, 0, 4600),
('F0000015', 1, 1, '2020-04-17 00:00:00', 784, 125.44, 0, 909.44),
('F0000016', 2, 1, '2020-05-03 00:00:00', 16950, 3051, 0, 20001),
('F0000017', 1, 1, '2020-05-04 00:00:00', 490, 88.2, 0, 578.2),
('F0000018', 2, 1, '2020-05-04 00:00:00', 980, 176.4, 0, 1156.4),
('F0000019', 2, 1, '2020-05-04 00:00:00', 980, 176.4, 0, 1156.4),
('F0000020', 1, 1, '2020-05-04 00:00:00', 425, 76.5, 0, 501.5),
('F0000021', 2, 1, '2020-05-04 00:00:00', 6780, 1220.4, 0, 8000.4),
('F0000022', 2, 1, '2020-05-04 00:00:00', 60, 10.8, 0, 70.8),
('F0000023', 1, 1, '2020-05-04 00:00:00', 980, 176.4, 0, 1156.4),
('F0000024', 2, 1, '2020-05-04 00:00:00', 490, 88.2, 0, 578.2),
('F0000025', 1, 1, '2020-05-04 00:00:00', 60, 10.8, 0, 70.8),
('F0000026', 2, 1, '2020-05-04 00:00:00', 980, 176.4, 0, 1156.4),
('F0000027', 2, 1, '2020-05-04 00:00:00', 13560, 2440.8, 0, 16000.8),
('F0000028', 2, 1, '2020-05-04 00:00:00', 490, 88.2, 0, 578.2),
('F0000029', 2, 1, '2020-05-04 00:00:00', 345, 62.1, 0, 407.1),
('F0000030', 3, 1, '2020-06-04 00:00:00', 1000, 120, 0, 1120),
('F0000031', 2, 1, '2020-06-05 00:00:00', 3608, 432.96, 0, 4040.96),
('F0000032', 5, 1, '2020-06-06 00:00:00', 65, 8.45, 0, 73.45),
('F0000033', 6, 1, '2020-06-06 00:00:00', 150, 15, 0, 165),
('F0000034', 7, 1, '2020-06-06 00:00:00', 360, 54, 0, 414),
('F0000035', 7, 1, '2020-06-06 00:00:00', 6780, 1017, 0, 7797);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aper_caja`
--
ALTER TABLE `aper_caja`
  ADD PRIMARY KEY (`id_aper_caja`),
  ADD KEY `id_caja` (`id_caja`),
  ADD KEY `id_user` (`id_user`);

--
-- Indices de la tabla `caja`
--
ALTER TABLE `caja`
  ADD PRIMARY KEY (`id_caja`);

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`cod_ciu`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`cod_cli`),
  ADD KEY `cod_ciu` (`cod_ciu`);

--
-- Indices de la tabla `det_caja`
--
ALTER TABLE `det_caja`
  ADD PRIMARY KEY (`id_det`),
  ADD KEY `id_per_caja` (`id_per_caja`),
  ADD KEY `det_caja_ibfk_2` (`cod_vntas`),
  ADD KEY `cod_pedido` (`cod_pedido`);

--
-- Indices de la tabla `det_pedidos`
--
ALTER TABLE `det_pedidos`
  ADD KEY `cod_ped` (`cod_ped`),
  ADD KEY `cod_prod` (`cod_prod`);

--
-- Indices de la tabla `det_ventas`
--
ALTER TABLE `det_ventas`
  ADD KEY `cod_vntas` (`cod_vntas`),
  ADD KEY `cod_prod` (`cod_prod`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`cod_empresa`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`cod_marca`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`cod_ped`),
  ADD KEY `cod_prov` (`cod_prov`),
  ADD KEY `cod_usu` (`cod_usu`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`cod_usu`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`cod_prod`),
  ADD KEY `cod_marca` (`cod_marca`),
  ADD KEY `cod_tip` (`cod_tip`);

--
-- Indices de la tabla `productos_compras`
--
ALTER TABLE `productos_compras`
  ADD PRIMARY KEY (`cod_prod`),
  ADD KEY `cod_marca` (`cod_marca`),
  ADD KEY `cod_tip` (`cod_tip`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`cod_prove`),
  ADD KEY `cod_ciu` (`cod_ciu`);

--
-- Indices de la tabla `tbajustes`
--
ALTER TABLE `tbajustes`
  ADD PRIMARY KEY (`idajuste`),
  ADD KEY `idmotivo` (`idmotivo`),
  ADD KEY `codprod` (`codprod`),
  ADD KEY `id_user` (`id_user`);

--
-- Indices de la tabla `tbmotivoajustes`
--
ALTER TABLE `tbmotivoajustes`
  ADD PRIMARY KEY (`idmotivo`);

--
-- Indices de la tabla `tipo`
--
ALTER TABLE `tipo`
  ADD PRIMARY KEY (`id_tipo`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`cod_vntas`),
  ADD KEY `cod_cli` (`cod_cli`),
  ADD KEY `cod_usu` (`cod_usu`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aper_caja`
--
ALTER TABLE `aper_caja`
  MODIFY `id_aper_caja` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `caja`
--
ALTER TABLE `caja`
  MODIFY `id_caja` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `det_caja`
--
ALTER TABLE `det_caja`
  MODIFY `id_det` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `cod_empresa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tbajustes`
--
ALTER TABLE `tbajustes`
  MODIFY `idajuste` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `tbmotivoajustes`
--
ALTER TABLE `tbmotivoajustes`
  MODIFY `idmotivo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aper_caja`
--
ALTER TABLE `aper_caja`
  ADD CONSTRAINT `aper_caja_ibfk_1` FOREIGN KEY (`id_caja`) REFERENCES `caja` (`id_caja`),
  ADD CONSTRAINT `aper_caja_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `personal` (`cod_usu`);

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`cod_ciu`) REFERENCES `ciudad` (`cod_ciu`);

--
-- Filtros para la tabla `det_caja`
--
ALTER TABLE `det_caja`
  ADD CONSTRAINT `det_caja_ibfk_1` FOREIGN KEY (`id_per_caja`) REFERENCES `aper_caja` (`id_aper_caja`),
  ADD CONSTRAINT `det_caja_ibfk_2` FOREIGN KEY (`cod_vntas`) REFERENCES `ventas` (`cod_vntas`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `det_caja_ibfk_3` FOREIGN KEY (`cod_pedido`) REFERENCES `pedidos` (`cod_ped`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `det_pedidos`
--
ALTER TABLE `det_pedidos`
  ADD CONSTRAINT `det_pedidos_ibfk_1` FOREIGN KEY (`cod_ped`) REFERENCES `pedidos` (`cod_ped`),
  ADD CONSTRAINT `det_pedidos_ibfk_2` FOREIGN KEY (`cod_prod`) REFERENCES `productos_compras` (`cod_prod`);

--
-- Filtros para la tabla `det_ventas`
--
ALTER TABLE `det_ventas`
  ADD CONSTRAINT `det_ventas_ibfk_1` FOREIGN KEY (`cod_vntas`) REFERENCES `ventas` (`cod_vntas`),
  ADD CONSTRAINT `det_ventas_ibfk_2` FOREIGN KEY (`cod_prod`) REFERENCES `producto` (`cod_prod`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`cod_prov`) REFERENCES `proveedor` (`cod_prove`),
  ADD CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`cod_usu`) REFERENCES `personal` (`cod_usu`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`cod_marca`) REFERENCES `marca` (`cod_marca`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`cod_tip`) REFERENCES `tipo` (`id_tipo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `productos_compras`
--
ALTER TABLE `productos_compras`
  ADD CONSTRAINT `productos_compras_ibfk_1` FOREIGN KEY (`cod_marca`) REFERENCES `marca` (`cod_marca`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `productos_compras_ibfk_2` FOREIGN KEY (`cod_tip`) REFERENCES `tipo` (`id_tipo`);

--
-- Filtros para la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD CONSTRAINT `proveedor_ibfk_1` FOREIGN KEY (`cod_ciu`) REFERENCES `ciudad` (`cod_ciu`);

--
-- Filtros para la tabla `tbajustes`
--
ALTER TABLE `tbajustes`
  ADD CONSTRAINT `tbajustes_ibfk_1` FOREIGN KEY (`idmotivo`) REFERENCES `tbmotivoajustes` (`idmotivo`),
  ADD CONSTRAINT `tbajustes_ibfk_2` FOREIGN KEY (`codprod`) REFERENCES `producto` (`cod_prod`),
  ADD CONSTRAINT `tbajustes_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `personal` (`cod_usu`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`cod_cli`) REFERENCES `cliente` (`cod_cli`),
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`cod_usu`) REFERENCES `personal` (`cod_usu`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
