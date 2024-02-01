<script setup>

</script>

<template>
    <ul class="tileList">
        <li class="tileListItem">
            <div class="tileContainer">
                <a class="tile">
                    <button type="button" class="tileButton" @click="post">abzbzbz</button>
                </a>
            </div>
        </li>
        <li class="tileListItem">
            <div class="tileContainer">
                <a class="tile">
                    <button type="button" class="tileButton" @click="post">abzbzbz</button>
                </a>
            </div>
        </li>
        <li class="tileListItem">
            <div class="tileContainer">
                <a class="tile">
                    <button type="button" class="tileButton" @click="post">abzbzbz</button>
                </a>
            </div>
        </li>
    </ul>
</template>

<script>
  export default {
    methods: {
        async post() {
            await fetch('terra/api/log').then(response => response.text())
            .then(data => console.log(data))
            .catch(error => console.log(error));
        }
    }
  }
</script>

<style scoped>
.tileList {
    position: relative;
    display: grid;
    list-style-type: none;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 10px;
    padding: 0;
    width: 346.41px;
    min-height: 300px;
    font-size: 0;
}

.tileList * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

.tileListItem {
    position: relative;
    display: inline;
    box-sizing: border-box;
    grid-column-end: span 2;
    outline: 1px solid transparent;
    border-bottom: 1px solid transparent;
}

.tileListItem:nth-child(3) {
    grid-column-start: 2;
}

.tileContainer, .tileContainer * {
    position: absolute;
}

.tileContainer {
    overflow: hidden;
    visibility: hidden;
    width: 100%;
    padding-bottom: 115.47%;
    transform: rotate3d(0, 0, 1, -60deg) skewY(30deg);
}

.tile {
    display: block;
    overflow: hidden;
    visibility: visible;
    width: 100%;
    height: 100%;
    background-color: green;
    transform: skewY(-30deg) rotate3d(0, 0, 1, 60deg);
}

.tileButton {
    border-width: 0;
    width: 100%;
    height: 100%;
}
</style>

