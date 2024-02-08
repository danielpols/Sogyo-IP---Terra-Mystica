<script setup>
</script>

<template>
    <svg class="dwelling" width="100px" height="100px">
        <polygon points="40,50 40,70 60,70 60,50" fill="none" stroke="black" stroke-width="3"/>
        <polygon points="40,50 60,50 50,40" fill="none" stroke="black" stroke-width="3"/>
    </svg>
</template>

<script>
</script>

<style scoped>
.dwelling {
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
</style>