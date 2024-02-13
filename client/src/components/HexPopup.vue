<script setup>
    import { gameState } from '@/global';
</script>

<template>
    <div>
        <div class="popupText">Boo!</div>
    </div>
</template>

<script>
</script>

<style scoped>
.popupText {
    display: block;
    position: relative;
    z-index: 1;

    border-radius: 6px;
    background-color: white;
    width: 100%;

    height: fit-content;

    text-align: center;
    color: black;
    font-size: 8px;
}
</style>