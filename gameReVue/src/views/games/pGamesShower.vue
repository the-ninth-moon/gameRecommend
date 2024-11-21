<template>
<div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="全部" name="zero">
        <!-- 搜索 -->
        <el-input size="small" v-model="input_title" placeholder="请输入标题，可回车搜索..." prefix-icon="el-icon-search"
            @keydown.enter.native="search_title"></el-input>
        <el-button size="small" type="primary" @click="search_title" icon="el-icon-search">搜索</el-button>
                <!-- 排序按钮 -->
                <el-button
          size="small"
          :type="sortType === 0 ? 'primary' : 'default'"
          @click="sortGames(0)"
        >
          按时间排序
        </el-button>
        <el-button
          size="small"
          :type="sortType === 9 ? 'primary' : 'default'"
          @click="sortGames(9)"
        >
          按热度排序
        </el-button>
        <!-- 用户前台游戏列表组件 -->
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 0 && hidden_searchcom == false"></pGamesShowerListCom>
        <!-- 用户前台游戏列表组件 -->
        <pGamesShowerListCom
          :tindex="tabindex"
          v-if="tabindex == 9 && hidden_searchcom == false"
        ></pGamesShowerListCom>
        <!-- 搜索专用组件 -->
        <pGamesShowerSearch v-if="tabindex == 0 && hidden_searchcom == true" :gamesData="gamesData"></pGamesShowerSearch>
    </el-tab-pane>

    <el-tab-pane label="角色扮演" name="first">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 1"></pGamesShowerListCom>
    </el-tab-pane>
    <el-tab-pane label="射击游戏" name="second">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 2"></pGamesShowerListCom>
    </el-tab-pane>
    <el-tab-pane label="即时战略" name="third">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 3"></pGamesShowerListCom>
    </el-tab-pane>
    <el-tab-pane label="动作冒险" name="fourth">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 4"></pGamesShowerListCom>
    </el-tab-pane>
    <el-tab-pane label="模拟经营" name="fifth">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 5"></pGamesShowerListCom>
    </el-tab-pane>
    <el-tab-pane label="体育竞技" name="sixth">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 6"></pGamesShowerListCom>
    </el-tab-pane>
    <el-tab-pane label="驾驶竞速" name="seventh">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 7"></pGamesShowerListCom>
    </el-tab-pane>
    <el-tab-pane label="音乐艺术" name="eighth">
        <pGamesShowerListCom :tindex="tabindex" v-if="tabindex == 8"></pGamesShowerListCom>
    </el-tab-pane>
    </el-tabs>
</div>
</template>

<script>
import pGamesShowerListCom from './pGamesShowerListCom.vue';
import pGamesShowerSearch from './pGamesShowerSearch.vue';

export default {
inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
name: 'pGamesShower',
components: {
    pGamesShowerListCom,
    pGamesShowerSearch
},
data() {
    return {
    activeName: 'zero',
    input_title: '', // 输入框的值
    gamesData: [], // 文章数据
    tabindex: "0", // 选项卡index
    hidden_searchcom: false, // 是否隐藏搜索子组件
    };
},
methods: {
    handleClick(tab, event) {
    this.tabindex = tab.index;
    // console.log("tabindex = " + this.tabindex);
    },
    sortGames(type) {
      this.sortType = type; // 更新排序类型
      this.tabindex = ''+type;
    },
    search_title() {
    if (this.input_title === '') {
        this.hidden_searchcom = false;
    } else {
        this.hidden_searchcom = true;
    }
    const _this = this;
    this.getRequest(`/game/getByTitle?title=${encodeURIComponent(this.input_title)}`).then(resp => {
        if (resp) {
        _this.gamesData = resp.data;
        }
    });
    }
}
};
</script>

<style scoped>
.el-tabs {
border-bottom: 2px solid #dcdfe6;
margin-top: 10px;
}

.el-tabs__item {
transition: color 0.3s;
font-size: 16px;
padding: 12px 24px;
}

.el-tabs__item.is-active {
color: #409eff;
border-bottom: 2px solid #409eff;
}

.el-tabs__item:hover {
color: #409eff;
}

.el-tabs__content {
padding-top: 20px;
}

.el-input {
margin-bottom: 10px;
width: 400px; margin-right: 10px;
}

.el-button {
margin-bottom: 10px;
}
</style>