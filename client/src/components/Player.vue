<script setup>
    import { tileColors } from '@/global';
</script>

<template>
    <li class="playerItem" :style="playerCSS">
        <div class="playerBoard">
            {{ player.name }} <br/>
            {{ player.terrain }}
        </div>
    </li>
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
    position: relative;
    background-color: var(--bg-color);
    color: black;
    width: 100%;
    padding-bottom: 40%;
}

.playerBoard {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
</style>