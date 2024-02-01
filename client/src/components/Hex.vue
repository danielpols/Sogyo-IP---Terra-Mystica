<script setup>

</script>

<template>
    <li class="tileListItem">
        <div class="tileContainer">
            <a class="tile">
                <button type="button" class="tileButton" @click="post">{{ terrain }}</button>
            </a>
        </div>
    </li>
</template>

<script>
  export default {
    props: ['terrain'],
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
.tileListItem {
    position: relative;
    display: inline;
    box-sizing: border-box;
    grid-column-end: span 2;
    outline: 1px solid transparent;
    border-bottom: 1px solid transparent;
    width: 100%;
    padding-bottom: 86.602%;
}

.tileListItem * {
    margin: 0;
    padding: 0;
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

