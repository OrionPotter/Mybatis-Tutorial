-- 创建Blog表
CREATE TABLE Blog (
    id INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    summary VARCHAR(255),
    tags VARCHAR(255),
    categories VARCHAR(255),
    status VARCHAR(50),
    views INT DEFAULT 0,
    likes INT DEFAULT 0,
    comments_count INT DEFAULT 0,
    slug VARCHAR(255),
    meta_description VARCHAR(255),
    meta_keywords VARCHAR(255),
    featured_image VARCHAR(255),
    is_featured BOOLEAN DEFAULT FALSE,
    allow_comments BOOLEAN DEFAULT TRUE
);

-- 插入10条数据
INSERT INTO Blog (id, title, content, author_id, created_at, updated_at, summary, tags, categories, status, views, likes, comments_count, slug, meta_description, meta_keywords, featured_image, is_featured, allow_comments) VALUES
(1, 'My First Blog Post', 'This is the content of my first blog post.', 1, '2023-06-11 10:00:00', '2023-06-11 10:00:00', 'This is a summary of my first blog post.', 'Java,Programming,Tutorial', 'Technology,Education', 'published', 100, 10, 5, 'my-first-blog-post', 'This is a meta description for SEO.', 'Java,Blog,Tutorial', 'http://example.com/image1.jpg', TRUE, TRUE),
(2, 'My Second Blog Post', 'This is the content of my second blog post.', 2, '2023-06-12 11:00:00', '2023-06-12 11:00:00', 'This is a summary of my second blog post.', 'Python,Programming,Guide', 'Technology,Education', 'published', 200, 20, 10, 'my-second-blog-post', 'This is a meta description for SEO.', 'Python,Blog,Guide', 'http://example.com/image2.jpg', FALSE, TRUE),
(3, 'My Third Blog Post', 'This is the content of my third blog post.', 3, '2023-06-13 12:00:00', '2023-06-13 12:00:00', 'This is a summary of my third blog post.', 'JavaScript,Programming,Example', 'Technology,Education', 'draft', 50, 5, 2, 'my-third-blog-post', 'This is a meta description for SEO.', 'JavaScript,Blog,Example', 'http://example.com/image3.jpg', TRUE, FALSE),
(4, 'My Fourth Blog Post', 'This is the content of my fourth blog post.', 4, '2023-06-14 13:00:00', '2023-06-14 13:00:00', 'This is a summary of my fourth blog post.', 'C++,Programming,Sample', 'Technology,Education', 'published', 300, 30, 15, 'my-fourth-blog-post', 'This is a meta description for SEO.', 'C++,Blog,Sample', 'http://example.com/image4.jpg', FALSE, TRUE),
(5, 'My Fifth Blog Post', 'This is the content of my fifth blog post.', 5, '2023-06-15 14:00:00', '2023-06-15 14:00:00', 'This is a summary of my fifth blog post.', 'Ruby,Programming,Tutorial', 'Technology,Education', 'published', 400, 40, 20, 'my-fifth-blog-post', 'This is a meta description for SEO.', 'Ruby,Blog,Tutorial', 'http://example.com/image5.jpg', TRUE, TRUE),
(6, 'My Sixth Blog Post', 'This is the content of my sixth blog post.', 6, '2023-06-16 15:00:00', '2023-06-16 15:00:00', 'This is a summary of my sixth blog post.', 'PHP,Programming,Guide', 'Technology,Education', 'draft', 60, 6, 3, 'my-sixth-blog-post', 'This is a meta description for SEO.', 'PHP,Blog,Guide', 'http://example.com/image6.jpg', FALSE, FALSE),
(7, 'My Seventh Blog Post', 'This is the content of my seventh blog post.', 7, '2023-06-17 16:00:00', '2023-06-17 16:00:00', 'This is a summary of my seventh blog post.', 'Go,Programming,Example', 'Technology,Education', 'published', 500, 50, 25, 'my-seventh-blog-post', 'This is a meta description for SEO.', 'Go,Blog,Example', 'http://example.com/image7.jpg', TRUE, TRUE),
(8, 'My Eighth Blog Post', 'This is the content of my eighth blog post.', 8, '2023-06-18 17:00:00', '2023-06-18 17:00:00', 'This is a summary of my eighth blog post.', 'Swift,Programming,Sample', 'Technology,Education', 'published', 600, 60, 30, 'my-eighth-blog-post', 'This is a meta description for SEO.', 'Swift,Blog,Sample', 'http://example.com/image8.jpg', FALSE, TRUE),
(9, 'My Ninth Blog Post', 'This is the content of my ninth blog post.', 9, '2023-06-19 18:00:00', '2023-06-19 18:00:00', 'This is a summary of my ninth blog post.', 'Kotlin,Programming,Tutorial', 'Technology,Education', 'draft', 70, 7, 4, 'my-ninth-blog-post', 'This is a meta description for SEO.', 'Kotlin,Blog,Tutorial', 'http://example.com/image9.jpg', TRUE, FALSE),
(10, 'My Tenth Blog Post', 'This is the content of my tenth blog post.', 10, '2023-06-20 19:00:00', '2023-06-20 19:00:00', 'This is a summary of my tenth blog post.', 'Scala,Programming,Guide', 'Technology,Education', 'published', 700, 70, 35, 'my-tenth-blog-post', 'This is a meta description for SEO.', 'Scala,Blog,Guide', 'http://example.com/image10.jpg', FALSE, TRUE);