import requests
import bs4
import os
import time
import re
import csv
import random

ip_pool = [
    '94.154.152.116:8079',
    '36.6.144.184:8089',
    '35.234.87.239:80',
    '103.147.247.15:3127',
    '1.224.3.122:3888',
    '60.188.5.211:80',
    '41.74.91.244:80',
    # 添加更多的IP地址...
]

proxy = random.choice(ip_pool)
proxies = {
    'http': 'http://124.243.226.18:8888',
    'https': 'https://124.243.226.18:8888'
}
headers = {
        'user-agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36'
}



page_num = 2354
page_num = 2354

game_list = []
start_write_num = 0

dict_headers = ['name', 'brief_info', 'score', 'gamecontent', 'config_require', 'diy_config_require', 'game_img']

game_csv = open('gamelist.csv', 'a', encoding='GB18030', newline="")

game_writer = csv.DictWriter(game_csv, dict_headers)
game_writer.writeheader()

count=0

for i in range(109, page_num + 1):
    print("第 %d 页" % i)
    # 从页面中得到游戏信息详情页
    page = requests.get('https://dl.3dmgame.com/all_all_' + str(i) + '_hot/', headers=headers,timeout=(3,8))

    soup1 = bs4.BeautifulSoup(page.text, "html.parser")

    url_list = soup1.body.find_all(class_='item')  # 得到网页链接的div
    page_list = []  # 存放网页链接
    for j in range(2, len(url_list)):
        page_list.append(url_list[j].a.get('href'))
    for url in page_list:
        html = requests.get(url, headers=headers,timeout=(3,8))

        html_view_source = html.content.decode('utf-8')
        # with open("2.txt",'w',encoding='utf-8') as f:
        #    f.write(html_view_source)
        soup2 = bs4.BeautifulSoup(html_view_source, "html.parser")

        # 从游戏信息页面中提取出信息来
        try:
            gameinfo = soup2.find(class_='gameinfo')
            name = re.match('《.+》', gameinfo.h1.text).group()
            a = gameinfo.ul.select('li')
            brief_info = {
                    'gameType':'无信息' if len(a[0].contents)<2  else a[0].contents[1].text,
                    'developer':'无信息' if len(a[1].contents)<2  else a[1].contents[1].text,
                    'sellTime':'无信息' if len(a[2].contents)<2  else a[2].contents[1].text,
                    'collectTime':'无信息' if len(a[3].contents)<2  else a[3].contents[1].text,
                    'gamePlatform':'无信息' if len(a[4].contents)<2  else a[4].contents[1].text,
                    'officialWeb':gameinfo.ul.select('a')[0].get('href') if a[5].text == '官方网站：点击进入' else '暂无',
                    'tag':'无信息' if len(a[6].contents)<2 else [i.text for i in a[6].contents if i.text!='标签：'],
                    'language':'无信息' if len(a[7].contents)<2 else a[7].contents[1].text
                }

            score = gameinfo.find(class_='processingbar').font.text

            gamecontent = soup2.find(class_='GmL_1')  # 疑似原网页前后端不分离，提取难度较大

            config_require = soup2.find(class_='GmL_2')  # 前端直接渲染成html?
            diy_config_require = soup2.find(class_='GmL_2_2')

            game_img = [i.get('src') for i in soup2.find(class_='GmL_4').find(class_='large_box').select('img')]
            # print(config_require)
            # 写成一个字典吧
            game = {
                'name': name if name is not None else "没有名字",
                'brief_info': brief_info if brief_info is not None else "无内容信息",
                'score': score if score is not None else "分数",
                'gamecontent': gamecontent if gamecontent is not None else "游戏内容",
                'config_require': config_require if config_require is not None else "无配置信息",
                'diy_config_require': diy_config_require if config_require is not None else "无diy配置信息",
                'game_img': game_img
            }
            game_list.append(game)
            game_writer.writerow(game)
        except Exception as e:
            print(repr(e))
        count+=1
        print('爬取了{}个游戏'.format(count))
        time.sleep(random.randint(0,2))
        if(count%100==0):
            time.sleep(10)
game_csv.close()
