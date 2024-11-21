import mysql.connector
import json
from mysql.connector import Error

# 数据库配置信息
db_config = {
    "host": "47.98.41.200",
    "database": "gamerec",
    "user": "root",
    "password": "@text2SQL"
}

# 插入游戏信息的 SQL 语句
insert_game_query = """
INSERT INTO t_game (
    name, typeId, developer, sell_time, collect_time, platform, official_web, score, description, 
    game_content, config_require, first_picture, published, share_statement, is_delete, 
    comment_count, recommend, update_time, views
) VALUES (
    %s, %s, %s, %s, CURRENT_TIMESTAMP, %s, %s, %s, %s, %s, %s, %s, 1, 1, 0, %s, 1, NULL, %s
)
"""


# 读取 JSON 文件
def read_games_data(json_file):
    with open(json_file, "r", encoding="utf-8") as file:
        data = json.load(file)
    return data


try:
    # 连接到数据库
    connection = mysql.connector.connect(**db_config)

    if connection.is_connected():
        print("成功连接到数据库")
        cursor = connection.cursor()

        # 读取 JSON 文件中的游戏数据
        games_data = read_games_data("games_data.json")

        # 转换数据格式为 SQL 插入所需的元组
        game_records = [
            (
                game["name"], game["typeId"], game["developer"], game["sell_time"], game["platform"],
                game["official_web"], game["score"], game["description"], game["game_content"],
                game["config_require"], game["first_picture"], game["comment_count"], game["views"]
            )
            for game in games_data
        ]

        # 插入游戏数据
        cursor.executemany(insert_game_query, game_records)
        connection.commit()
        print(f"成功插入 {cursor.rowcount} 条数据")

except Error as e:
    print(f"数据库操作失败: {e}")

finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
        print("数据库连接已关闭")
