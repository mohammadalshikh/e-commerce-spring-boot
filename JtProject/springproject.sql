USE springproject;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `categories` (
    `categoryid` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `categories` (`categoryid`, `name`) VALUES
    (1, 'Fruits'),
    (2, 'Vegetables'),
    (3, 'Meats');





CREATE TABLE `login` (
    `id` int(11) NOT NULL,
    `password` varchar(20) NOT NULL,
    `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



INSERT INTO `login` (`id`, `password`, `username`) VALUES
    (1, '123', '1');



CREATE TABLE `products` (
    `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `image` text NOT NULL,
    `categoryid` int(11) NOT NULL,
    `quantity` int(11) NOT NULL,
    `price` int(11) NOT NULL,
    `weight` int(11) NOT NULL,
    `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



INSERT INTO `products` (`id`, `name`, `image`, `categoryid`, `quantity`, `price`, `weight`, `description`) VALUES
    (1, 'Orange', 'https://clipart-library.com/images_k/orange-transparent-background/orange-transparent-background-2.png', 1, 484, 3, 40, 'Sweet'),
    (2, 'Onion', 'https://clipart-library.com/images_k/onion-transparent-background/onion-transparent-background-16.png', 2, 352, 3, 40, 'Fresh'),
    (3, 'Beef', 'https://clipart-library.com/images_k/meat-transparent/meat-transparent-4.png', 3, 321, 30, 1000, 'Fresh'),
    (4, 'Apple', 'https://clipart-library.com/img/1565435.png', 1, 593, 2, 30, ''),
    (5, 'Watermelon', 'https://clipart-library.com/new_gallery/44-444830_share-this-article-watermelon-png.png', 1, 423, 10, 1000, ''),
    (6, 'Banana', 'https://clipart-library.com/image_gallery2/Banana.png', 1, 463, 2, 1000, ''),
    (7, 'Grapes', 'https://clipart-library.com/new_gallery/11-119102_frutas-grape-png.png', 1, 481, 5, 340, ''),
    (8, 'Pineapple', 'https://static.vecteezy.com/system/resources/previews/008/848/362/original/fresh-pineapple-free-png.png', 1, 294, 6, 500, ''),
    (9, 'Lettuce', 'https://www.pngmart.com/files/16/Green-Lettuce-PNG-Transparent-Image.png', 2, 234, 4, 300, ''),
    (10, 'Tomato', 'https://cdn.discordapp.com/attachments/1106825532508733460/1136204069439016960/image-removebg-preview.png', 1, 222, 2, 30, ''),
    (11, 'Corn', 'https://www.pngall.com/wp-content/uploads/2016/05/Corn-Free-Download-PNG.png', 2, 220, 4, 160, ''),
    (12, 'Cucumber', 'https://www.freepnglogos.com/uploads/cucumber-png/cucumber-png-image-purepng-transparent-png-26.png', 2, 120, 2, 30, ''),
    (13, 'Potato', 'https://clipart-library.com/image_gallery2/Potato-Free-Download-PNG.png', 2, 190, 2, 40, ''),
    (14, 'Cantaloupe', 'https://www.pngmart.com/files/15/Yellow-Cantaloupe-PNG-Transparent-Image.png', 1, 139, 6, 500, '');




CREATE TABLE `users` (
    `user_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username` varchar(45) NOT NULL,
    `password` varchar(64) NOT NULL,
    `role` varchar(250) NOT NULL DEFAULT 'ROLE_USER',
    `address` varchar(255) DEFAULT NULL,
    `email` varchar(110) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




INSERT INTO `users` (`user_id`, `username`, `password`, `role`, `address`, `email`) VALUES
    (1, 'jay', '123', 'ROLE_USER', NULL, 'jay190912@gmail.com'),
    (2, 'admin', '123', 'ROLE_ADMIN', NULL, 'admin190912@gmail.com');



ALTER TABLE `products`
    ADD KEY `products_ibfk_1` (`categoryid`),
    ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`categoryid`) REFERENCES `categories` (`categoryid`) ON DELETE CASCADE;
COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

create table TransactionHistory(
    userID int,
    transactionID int,
    productID int,
    quantity int,
    primary key (userID, transactionID, productID),
    foreign key (userID) references users(user_id),
    foreign key (productID) references products(id)
);

create table Cart(
    userID int,
    productID int,
    quantity int,
    primary key (userID, productID),
    foreign key (userID) references users(user_id),
    foreign key (productID) references products(id)
);
create table CustomCart(
    userID int,
    productID int,
    quantity int,
    primary key (userID, productID),
    foreign key (userID) references users(user_id),
    foreign key (productID) references products(id)
);

alter table products add discount double default 0.00;

alter table users add coupons int;


INSERT INTO `cart` (`userID`, `productID`, `quantity`)
VALUES
    (1, 1, 2),
    (1, 3, 1);


INSERT INTO `CustomCart` (`userID`, `productID`, `quantity`)
VALUES
    (1, 1, 2),
    (1, 3, 1),
    (1, 2, 3);


CREATE TABLE ProductMatrix (
    product int,
    p1 INT DEFAULT 0,
    p2 INT DEFAULT 0,
    p3 INT DEFAULT 0,
    p4 INT DEFAULT 0,
    p5 INT DEFAULT 0,
    p6 INT DEFAULT 0,
    p7 INT DEFAULT 0,
    p8 INT DEFAULT 0,
    p9 INT DEFAULT 0,
    p10 INT DEFAULT 0,
    p11 INT DEFAULT 0,
    p12 INT DEFAULT 0,
    p13 INT DEFAULT 0,
    p14 INT DEFAULT 0,

    FOREIGN KEY (product) REFERENCES products(id)
);

INSERT INTO ProductMatrix (product) VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14);



INSERT INTO categories (`categoryid`, `name`) VALUES (0, 'Coupons');

INSERT INTO products (`id`, `name`, `image`, `categoryid`, `quantity`, `price`, `weight`, `description`) VALUES (0, 'Coupon', 'https://cutewallpaper.org/24/coupon-png/download-food-coupons-full-size-png-image-pngkit.png', 0, 1, 0, 0, 'Save $5');



ALTER TABLE products ADD productPair INT DEFAULT 0;
ALTER TABLE products ADD FOREIGN KEY (productPair) REFERENCES products(id);

ALTER TABLE products ADD suggestedItem INT DEFAULT 0;
ALTER TABLE products ADD FOREIGN KEY (suggestedItem) REFERENCES products(id);

ALTER TABLE products modify price float DEFAULT 0;

ALTER TABLE users ADD cumulativeTotal FLOAT DEFAULT 0;

alter table users drop column coupons;
alter table users add coupons int default 0;

update users
set coupons = 10
where user_id = 1;