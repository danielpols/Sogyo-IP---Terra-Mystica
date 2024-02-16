<script setup>
    import { tileColors, doAction} from '@/global';
</script>

<template>
    <li class="playerItem" :style="playerCSS">
        <div class="playerBoard">
            <ul class="playerSubList">
                <li class="playerSubListItem">
                    {{ player.name }}<br/> 
                    {{ player.resources[0]+"/"+player.resources[1]+"/"+player.resources[2] }}<br/>
                    {{ player.terrain }} <br/>
                    <button class="passButton" v-if="player.passAction != null" type="button" @click="passTurn">Pass</button>
                </li>
                <li class="playerSubListItem">
                    Shipping: {{ player.shippingRange }}
                    <button class="sailButton" v-if="player.shippingAction != null" type="button">Upgrade!</button>
                </li>
                <li class="playerSubListItem">
                    Terraform cost:<br/>({{ player.terraformCost[0] }}/{{ player.terraformCost[1] }}/{{ player.terraformCost[2] }})
                    <button class="shovelButton" v-if="player.shovelAction != null" type="button">Upgrade!</button>
                </li>
            </ul>
        </div>
    </li>
</template>

<script>
    export default {
        props: ['player'],
        methods: {
            async passTurn() {
                await doAction(this.player.passAction);
            },
            async upgradeShipping() {
                await doAction(this.playser.shippingAction);
            },
            async upgradeShovel() {
                await doAction(this.player.shovelAction);
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
    width: 100%;
    height: 100%;

    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}

.playerSubList {
    position: relative;
    display: grid;
    margin: 0;
    padding: 0;
    list-style-type: none;
    grid-template-columns: 1fr 1fr;
    width: 100%;
    height: 100%;
}

.playerSubListItem {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.playerSubListItem:first-child{
    grid-row-end: span 2;
}

.passButton {
    width: 50%;
    margin: 0 auto;
}

.sailButton, .shovelButton {
    width: 40%;
    margin: 0 auto;
}
</style>