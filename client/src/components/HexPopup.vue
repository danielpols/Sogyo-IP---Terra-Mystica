<script setup>
    import { gameState } from '@/global';
</script>

<template>
    <span class="popupText">Boo!</span>
</template>

<script>
</script>

<style scoped>
.popupText {
    z-index: 100;
    position: absolute;
    background-color: white;
    border-radius: 2px;
    width: 150%;

    text-align: center;

    left: 50%;
    bottom: 50%;
    transform: translateX(-50%);
}
</style>