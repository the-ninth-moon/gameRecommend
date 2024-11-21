<template>
    <canvas id="captchaCanvas" @click="generateCaptcha" :style="{width,height}"></canvas>
</template>

<script setup>
import {watch, onMounted, toRefs} from "vue";
const emit=defineEmits(['getValue'])
const props = defineProps({
 // 宽度
 width: {
   type: String,
   default: '100%'
 },
 // 高度
 height: {
   type: String,
   default: '100%'
 },
 //验证码数量
 number: {
   type: Number,
   default: 4,
 }, //验证码数量
 night: {
   type: Boolean,
   default: false,
 },
});
watch(()=>props.night,()=>{
 generateCaptcha()
})
const  generateCaptcha=()=> {

 const canvas = document.getElementById('captchaCanvas');
 const ctx = canvas.getContext('2d');
    // 设置画布实际尺寸
    canvas.width = parseInt(props.width.replace('px', '')) || canvas.offsetWidth;
    canvas.height = parseInt(props.height.replace('px', '')) || canvas.offsetHeight;

    // 清除旧内容
    ctx.clearRect(0, 0, canvas.width, canvas.height);
 // 字符集
 const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
 let captchaText = '';
 // 设置背景颜色和一些基本样式
 ctx.fillStyle = props.night?'#2d2c2c':'#f9f9f9';
 ctx.fillRect(0, 0, canvas.width, canvas.height);
 // 绘制噪点
 for (let i = 0; i < 200; i++) {
   ctx.fillStyle = getRandomColor(0, 300);
   ctx.fillRect(Math.random() * canvas.width, Math.random() * canvas.height, 1, 1);
 }
 // 绘制干扰线
 for (let i = 0; i < 9; i++) {
   ctx.strokeStyle = getRandomColor(150, 255);
   ctx.beginPath();
   ctx.moveTo(Math.random() * canvas.width, Math.random() * canvas.height);
   ctx.lineTo(Math.random() * canvas.width, Math.random() * canvas.height);
   ctx.stroke();
 }
 // 生成验证码文本
 for (let i = 0; i < props.number; i++) { 
   const randomIndex = Math.floor(Math.random() * chars.length);
   const char = chars[randomIndex];
   captchaText += char;
   drawChar(char, 1 + i * canvas.width/props.number, (canvas.height/2)+20, 70, getRandomColor(10, 50));
 }
 //返回给父组件
 emit('getValue',captchaText)
}
const drawChar=(char, x, y, fontSize, fillStyle)=> {
 const ctx = document.getElementById('captchaCanvas').getContext('2d');
 ctx.font = fontSize + 'px Arial';
 ctx.fillStyle = fillStyle;
 ctx.fillText(char, x, y);
}

const  getRandomColor=(min, max)=> {
 var r = Math.floor(Math.random() * 256); // 0-255
 var g = Math.floor(Math.random() * 256);
 var b = Math.floor(Math.random() * 256);
 return `rgb(${r},${g},${b})`;
}
//暴露给父组件调用
defineExpose({generateCaptcha})
onMounted(()=>{
 generateCaptcha();
})
</script>

<style scoped>
#captchaCanvas {
 margin-bottom: 10px;
 display: block;
 user-select: none;
}
</style>
