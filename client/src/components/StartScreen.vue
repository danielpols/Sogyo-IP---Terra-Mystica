<script setup>
    import { gameState } from '../global.js';
</script>

<template>
    <div>
        <ul class="playerList">
            <li v-for="(player, i) in playerList" :key="player">
                <input class="playerNameInput" type="text" @input="event => playerList[i].name = event.target.value">
                <select class="playerTerrainInput" @input="event => playerList[i].terrain = event.target.value">
                    <option value="PLAINS">Plains</option>
                    <option value="SWAMP">Swamp</option>
                    <option value="LAKE">Lake</option>
                    <option value="FOREST">Forest</option>
                    <option value="MOUNTAINS">Mountains</option>
                    <option value="WASTELAND">Wasteland</option>
                    <option value="DESERT">Desert</option>
                </select>
                <button v-if="playerList.length > 2" type="button" @click="playerList.splice(i, 1)">Remove</button>
            </li>
            <li v-if="playerList.length < 5"><button type="button" @click="addPlayer">Add another player</button></li>
            <li><button type="button" @click="logPlayers">We do a little logging</button></li>
        </ul>
    </div>
    <button type="button" v-on:click="getGameState">Fetch!</button>
</template>

<script>
    export default {
        data () {
            return {
                playerList: []
            };
        },
        methods: {
            addPlayer() {
                this.playerList.push({
                    name: "",
                    terrain: "PLAINS"
                });
            },
            logPlayers() {
                console.log(this.playerList);
            },
            removePlayer(index) {
                this.$delete(this.playerList, index);
            },
            async getGameState() {
                await fetch('terra/api/start', {
                method: "POST",
                headers: {
                    Accept: "application/json",
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.playerList)
                }).then(response => response.json())
                .then(data => {console.log("Success!"); gameState.state = data})
                .catch(error => console.log(error));
            }
        },
        beforeMount() {
            this.addPlayer();
            this.addPlayer();
        }
    }
</script>

<style scoped>
.playerList {
    list-style-type: none;
    padding: 0;
}
</style>