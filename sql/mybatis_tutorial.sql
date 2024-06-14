SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int NOT NULL,
  `street` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '123 Main St', 'Springfield');
INSERT INTO `address` VALUES (2, '456 Elm St', 'Shelbyville');

-- ----------------------------
-- Table structure for animals
-- ----------------------------
DROP TABLE IF EXISTS `animals`;
CREATE TABLE `animals`  (
  `id` int NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `breed` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of animals
-- ----------------------------
INSERT INTO `animals` VALUES (1, 'Whiskers', 'cat', 'Siamese', 'Cream');
INSERT INTO `animals` VALUES (2, 'Rover', 'dog', 'Labrador', 'Black');

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `author_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `categories` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `views` int NULL DEFAULT 0,
  `likes` int NULL DEFAULT 0,
  `comments_count` int NULL DEFAULT 0,
  `slug` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `meta_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `meta_keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `featured_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `is_featured` int NULL DEFAULT 0,
  `allow_comments` int NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, 'My First Blog Post', 'This is the content of my first blog post.', 1, '2023-06-11 10:00:11', '2023-06-11 10:00:00', 'This is a summary of my first blog post.', 'Java,Programming,Tutorial', 'Technology,Education', 'published', 100, 10, 5, 'my-first-blog-post', 'This is a meta description for SEO.', 'Java,Blog,Tutorial', 'http://example.com/image1.jpg', 1, 1);
INSERT INTO `blog` VALUES (2, 'My Second Blog Post', 'This is the content of my second blog post.', 2, '2023-06-12 11:00:00', '2023-06-12 11:00:00', 'This is a summary of my second blog post.', 'Python,Programming,Guide', 'Technology,Education', 'published', 200, 20, 10, 'my-second-blog-post', 'This is a meta description for SEO.', 'Python,Blog,Guide', 'http://example.com/image2.jpg', 0, 1);
INSERT INTO `blog` VALUES (3, 'My Third Blog Post', 'This is the content of my third blog post.', 3, '2023-06-13 12:00:00', '2023-06-13 12:00:00', 'This is a summary of my third blog post.', 'JavaScript,Programming,Example', 'Technology,Education', 'draft', 50, 5, 2, 'my-third-blog-post', 'This is a meta description for SEO.', 'JavaScript,Blog,Example', 'http://example.com/image3.jpg', 1, 0);
INSERT INTO `blog` VALUES (4, 'My Fourth Blog Post', 'This is the content of my fourth blog post.', 4, '2023-06-14 13:00:00', '2023-06-14 13:00:00', 'This is a summary of my fourth blog post.', 'C++,Programming,Sample', 'Technology,Education', 'published', 300, 30, 15, 'my-fourth-blog-post', 'This is a meta description for SEO.', 'C++,Blog,Sample', 'http://example.com/image4.jpg', 0, 1);
INSERT INTO `blog` VALUES (5, 'My Fifth Blog Post', 'This is the content of my fifth blog post.', 5, '2023-06-15 14:00:00', '2023-06-15 14:00:00', 'This is a summary of my fifth blog post.', 'Ruby,Programming,Tutorial', 'Technology,Education', 'published', 400, 40, 20, 'my-fifth-blog-post', 'This is a meta description for SEO.', 'Ruby,Blog,Tutorial', 'http://example.com/image5.jpg', 1, 1);
INSERT INTO `blog` VALUES (6, 'My Sixth Blog Post', 'This is the content of my sixth blog post.', 6, '2023-06-16 15:00:00', '2023-06-16 15:00:00', 'This is a summary of my sixth blog post.', 'PHP,Programming,Guide', 'Technology,Education', 'draft', 60, 6, 3, 'my-sixth-blog-post', 'This is a meta description for SEO.', 'PHP,Blog,Guide', 'http://example.com/image6.jpg', 0, 0);
INSERT INTO `blog` VALUES (7, 'My Seventh Blog Post', 'This is the content of my seventh blog post.', 7, '2023-06-17 16:00:00', '2023-06-17 16:00:00', 'This is a summary of my seventh blog post.', 'Go,Programming,Example', 'Technology,Education', 'published', 500, 50, 25, 'my-seventh-blog-post', 'This is a meta description for SEO.', 'Go,Blog,Example', 'http://example.com/image7.jpg', 1, 1);
INSERT INTO `blog` VALUES (8, 'My Eighth Blog Post', 'This is the content of my eighth blog post.', 8, '2023-06-18 17:00:00', '2023-06-18 17:00:00', 'This is a summary of my eighth blog post.', 'Swift,Programming,Sample', 'Technology,Education', 'published', 600, 60, 30, 'my-eighth-blog-post', 'This is a meta description for SEO.', 'Swift,Blog,Sample', 'http://example.com/image8.jpg', 0, 1);
INSERT INTO `blog` VALUES (9, 'My Ninth Blog Post', 'This is the content of my ninth blog post.', 9, '2023-06-19 18:00:00', '2023-06-19 18:00:00', 'This is a summary of my ninth blog post.', 'Kotlin,Programming,Tutorial', 'Technology,Education', 'draft', 70, 7, 4, 'my-ninth-blog-post', 'This is a meta description for SEO.', 'Kotlin,Blog,Tutorial', 'http://example.com/image9.jpg', 1, 0);
INSERT INTO `blog` VALUES (10, 'My Tenth Blog Post', 'This is the content of my tenth blog post.', 10, '2023-06-20 19:00:00', '2023-06-20 19:00:00', 'This is a summary of my tenth blog post.', 'Scala,Programming,Guide', 'Technology,Education', 'published', 700, 70, 35, 'my-tenth-blog-post', 'This is a meta description for SEO.', 'Scala,Blog,Guide', 'http://example.com/image10.jpg', 0, 1);

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`  (
  `id` int NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES (1, '1984', 'George Orwell', 9.99);
INSERT INTO `books` VALUES (2, 'To Kill a Mockingbird', 'Harper Lee', 7.99);

-- ----------------------------
-- Table structure for foods
-- ----------------------------
DROP TABLE IF EXISTS `foods`;
CREATE TABLE `foods`  (
  `id` int NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `available` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of foods
-- ----------------------------
INSERT INTO `foods` VALUES (1, 'Apple', 'Fruit', 1.99, 1);
INSERT INTO `foods` VALUES (2, 'Banana', 'Fruit', 0.59, 1);
INSERT INTO `foods` VALUES (3, 'Carrot', 'Vegetable', 0.49, 1);
INSERT INTO `foods` VALUES (4, 'Broccoli', 'Vegetable', 1.29, 1);
INSERT INTO `foods` VALUES (5, 'Chicken Breast', 'Meat', 3.99, 1);
INSERT INTO `foods` VALUES (6, 'Salmon Fillet', 'Fish', 8.99, 1);
INSERT INTO `foods` VALUES (7, 'Milk', 'Dairy', 1.49, 1);
INSERT INTO `foods` VALUES (8, 'Cheddar Cheese', 'Dairy', 2.99, 1);
INSERT INTO `foods` VALUES (9, 'Bread', 'Bakery', 1.99, 1);
INSERT INTO `foods` VALUES (10, 'Chocolate Cake', 'Bakery', 4.99, 1);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 1);
INSERT INTO `order` VALUES (2, 2);

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`  (
  `id` int NOT NULL,
  `order_id` int NULL DEFAULT NULL,
  `product_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `quantity` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES (1, 1, 'Item A', 2);
INSERT INTO `orderitem` VALUES (2, 1, 'Item B', 1);
INSERT INTO `orderitem` VALUES (3, 2, 'Item C', 5);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `address_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'John Doe', 1);
INSERT INTO `user` VALUES (2, 'Jane Smith', 2);

SET FOREIGN_KEY_CHECKS = 1;
