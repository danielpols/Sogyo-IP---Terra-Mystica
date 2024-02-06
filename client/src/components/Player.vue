<script setup>
    import { tileColors } from '@/global';
</script>

<template>
    <li class="playerItem" :style="playerCSS">{{ player.name }}</li>
</template>

<script>
    export default {
        props: ['player'],
        computed: {
            playerCSS() {
                return {
                    '--bg-color': tileColors(this.player.terrain)
                }
            }
        }
    }

</script>

<style scoped>
.playerItem {
    background-color: var(--bg-color);
    color: black;
}
</style>