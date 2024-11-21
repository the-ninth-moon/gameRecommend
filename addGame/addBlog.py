import pymysql
import json

# 数据库连接信息
db_config = {
    "host": "47.98.41.200",
    "user": "root",
    "password": "@text2SQL",
    "database": "gamerec",
    "charset": "utf8"
}

# 读取 JSON 数据
blog_data_file = "blogs_data.json"
with open(blog_data_file, "r", encoding="utf-8") as file:
    blog_data = json.load(file)

# 插入数据的 SQL 语句
insert_sql = """
INSERT INTO t_blog (
    game_id, userId, title, description, first_picture, content, original, 
    published, processed, share_statement, isDelete, comment_count, 
    recommend, views
) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
"""

try:
    # 建立数据库连接
    connection = pymysql.connect(**db_config)
    cursor = connection.cursor()

    # 遍历数据并插入
    for blog in blog_data:
        cursor.execute(
            insert_sql,
            (
                blog["game_id"], blog["userId"], blog["title"], blog["description"],
                blog["first_picture"], blog["content"], blog["original"],
                blog["published"], blog["processed"], blog["share_statement"],
                blog["isDelete"], blog["comment_count"], blog["recommend"], blog["views"]
            )
        )

    # 提交事务
    connection.commit()
    print(f"成功插入 {len(blog_data)} 条数据！")

except pymysql.MySQLError as e:
    print(f"数据库错误：{e}")
    connection.rollback()

finally:
    # 关闭连接
    if cursor:
        cursor.close()
    if connection:
        connection.close()
