<script setup>
    import { tileColors, doAction} from '@/global';
</script>

<template>
    <li class="playerItem" :style="playerCSS">
        <div class="playerBoard">
            {{ player.name }} <br/>
            {{ player.terrain }} <br/>
            <button v-if="player.passAction != null" type="button" @click="passTurn">Pass</button>
        </div>
    </li>
</template>

<script>
    export default {
        props: ['player'],
        methods: {
            async passTurn() {
                await doAction(this.player.passAction);
            }
        },
        computed: {
            playerCSS() {
                return {
                    '--bg-color': tileColors(this.player.terrain),
                    '--player-span-col' : this.player.turn ? 2 : 1,
                    '--player-span-row' : this.player.turn ? 3 : 2
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
    grid-column-end: span var(--player-span-col);
    grid-row-end: span var(--player-span-row);
}

.playerBoard {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
</style>