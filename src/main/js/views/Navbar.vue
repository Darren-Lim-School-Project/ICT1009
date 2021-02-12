<template>
    <b-navbar wrapper-class="container">
        <template slot="brand">
            <b-navbar-item tag="router-link" :to="{ name: 'index' }"
                >🚢
            </b-navbar-item>
        </template>
        <template slot="start">
            <b-navbar-item tag="router-link" :to="{ name: 'index' }"
                >Refinitiv
            </b-navbar-item>
            <b-navbar-dropdown label="Metamaze">
                <b-navbar-item tag="router-link" :to="{ name: 'index' }"
                    >Uploaded</b-navbar-item
                >
            </b-navbar-dropdown>
        </template>
    </b-navbar>
</template>

<script>
export default {
    name: "Navbar",
};
</script>
