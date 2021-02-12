<template>
    <div>
        <Navbar />
        <div class="tabs is-centered">
            <ul>
                <router-link tag="li" to="/hunt"><a>Hunt</a></router-link>
                <router-link tag="li" to="/status"><a>Status</a></router-link>
            </ul>
        </div>
        <div class="container px-3">
            <router-view></router-view>
        </div>
    </div>
</template>
<script>
import Navbar from "@/views/Navbar";

export default {
    name: "App",
    components: { Navbar }
};
</script>
