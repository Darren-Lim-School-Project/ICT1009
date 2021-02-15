<template>
    <div>
        <Navbar />
        <div class="tabs is-centered">
            <ul>
                <router-link tag="li" to="/"><a>Camp</a></router-link>
                <router-link tag="li" to="/travel"><a>Travel</a></router-link>
                <router-link tag="li" to="/inventory"
                    ><a>Inventory</a></router-link
                >
                <router-link tag="li" to="/shops"><a>Shops</a></router-link>
                <router-link tag="li" to="/mice"><a>Mice</a></router-link>
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
    components: { Navbar },
};
</script>
