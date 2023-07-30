# SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
# START TRANSACTION;
# SET time_zone = "+00:00";
#
# /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
# /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
# /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
# /*!40101 SET NAMES utf8mb4 */;
#
#
# CREATE TABLE `categories` (
#   `categoryid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
#   `name` varchar(255) NOT NULL
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
#
#
# INSERT INTO `categories` (`categoryid`, `name`) VALUES
# (1, 'Fruits'),
# (2, 'Vegetables'),
# (3, 'Meats');
#
#
#
#
#
# CREATE TABLE `login` (
#   `id` int(11) NOT NULL,
#   `password` varchar(20) NOT NULL,
#   `username` varchar(20) NOT NULL
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
#
#
#
# INSERT INTO `login` (`id`, `password`, `username`) VALUES
# (1, '123', '1');
#
#
#
# CREATE TABLE `products` (
#   `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
#   `name` varchar(255) NOT NULL,
#   `image` text NOT NULL,
#   `categoryid` int(11) NOT NULL,
#   `quantity` int(11) NOT NULL,
#   `price` int(11) NOT NULL,
#   `weight` int(11) NOT NULL,
#   `description` text NOT NULL
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
#
#
#
# INSERT INTO `products` (`id`, `name`, `image`, `categoryid`, `quantity`, `price`, `weight`, `description`) VALUES
# (1, 'Orange', '', 1, 484, 16, 13, 'Sweet'),
# (2, 'Onion', '', 2, 352, 12, 17, 'Fresh'),
# (3, 'Beef', '', 3, 321, 30, 50, 'Fresh');
#
#
#
#
# CREATE TABLE `users` (
#   `user_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
#   `username` varchar(45) NOT NULL,
#   `password` varchar(64) NOT NULL,
#   `role` varchar(250) NOT NULL DEFAULT 'ROLE_USER',
#   `address` varchar(255) DEFAULT NULL,
#   `email` varchar(110) NOT NULL,
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
#
#
#
#
# INSERT INTO `users` (`user_id`, `username`, `password`, `role`, `address`, `email`) VALUES
# (1, 'jay', '123', 'ROLE_USER', NULL, 'jay190912@gmail.com'),
# (2, 'admin', '123', 'ROLE_ADMIN', NULL, 'admin190912@gmail.com');
#
#
#
# ALTER TABLE `products`
# ADD KEY `products_ibfk_1` (`categoryid`),
# ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`categoryid`) REFERENCES `categories` (`categoryid`) ON DELETE CASCADE;
# COMMIT;
#
#
#
# /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
# /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
# /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
#
# create table TransactionHistory(
#                                    userID int,
#                                    transactionID int,
#                                    productID int,
#                                    quantity int,
#                                    primary key (userID, transactionID, productID),
#                                    foreign key (userID) references users(user_id),
#                                    foreign key (productID) references products(id)
# );
#
# create table Cart(
#                      userID int,
#                      productID int,
#                      quantity int,
#                      primary key (userID, productID),
#                      foreign key (userID) references users(user_id),
#                      foreign key (productID) references products(id)
# );
# create table CustomCart(
#                            userID int,
#                            productID int,
#                            quantity int,
#                            primary key (userID, productID),
#                            foreign key (userID) references users(user_id),
#                            foreign key (productID) references products(id)
# );
#
# alter table products add discount double default 0.00;
#
# alter table users add coupons int;
#
#
# INSERT INTO `cart` (`userID`, `productID`, `quantity`)
# VALUES
#     (1, 1, 2),
#     (1, 3, 1),
#     (2, 2, 3);