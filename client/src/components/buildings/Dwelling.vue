<script setup>
</script>

<template>
    <svg class="dwelling" viewBox="0 0 100 100">
        <polygon points="40,50 40,70 60,70 60,50" fill="none" stroke="black" stroke-width="3"/>
        <polygon points="40,50 60,50 50,40" fill="none" stroke="black" stroke-width="3"/>
    </svg>
</template>

<script>
</script>

<style scoped>
.dwelling {
    position: relative;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
    height: 100%;
}
</style>