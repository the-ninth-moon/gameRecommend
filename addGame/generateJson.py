import json
import random
from datetime import datetime, timedelta

# 游戏类型对应约定
game_types = {
    1: "角色扮演（RPG）",
    2: "射击游戏（FPS）",
    3: "即时战略（RTS）",
    4: "动作冒险（AVG）",
    5: "模拟经营（SIM）",
    6: "体育竞技（SPG）",
    7: "驾驶竞速（RAC）",
    8: "音乐艺术（MUG）"
}

# 随机生成数据
def random_date(start, end):
    """生成随机日期"""
    return start + timedelta(days=random.randint(0, (end - start).days))

def generate_game_data(num_records=50):
    """生成游戏数据"""
    data = []
    for i in range(num_records):
        type_id = random.randint(1, 8)
        game = {
            "name": f"游戏_{i+1}",
            "typeId": type_id,
            "developer": f"开发商_{random.randint(1, 20)}",
            "sell_time": random_date(datetime(2000, 1, 1), datetime(2024, 11, 18)).strftime("%Y-%m-%d"),
            "platform": random.choice(["PC", "PS4", "PS5", "Xbox", "Switch", "PC, PS5"]),
            "official_web": f"https://game_{i+1}.example.com",
            "score": round(random.uniform(6.0, 10.0), 1),
            "description": f"这是一款{game_types[type_id]}游戏。",
            "game_content": f"游戏_{i+1}的主要内容描述，包含大量有趣的元素。",
            "config_require": f"最低配置：i5 处理器, 8GB 内存, GTX {random.choice([1050, 1060, 1660, 2060])} 显卡",
            "first_picture": f"https://example.com/game_{i+1}.jpg",
            "comment_count": random.randint(1000, 100000),
            "views": random.randint(10000, 1000000)
        }
        data.append(game)
    return data

# 生成数据并保存为 JSON 文件
games_data = generate_game_data(500)

with open("games_data.json", "w", encoding="utf-8") as file:
    json.dump(games_data, file, ensure_ascii=False, indent=4)

print("生成的游戏数据已保存到 games_data.json 文件中！")
