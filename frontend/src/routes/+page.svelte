<script>
    import {onMount} from "svelte";
    let posts = [];

    let token = "";

    onMount(async () => {
        const res = await fetch("/api/post");
        posts = await res.json();
        token = localStorage.getItem("token");
    });
</script>

{#if token}
    <a href="/forums/create">Create Forum</a>
{/if}
<ul>
    {#each posts as post}
        <li>{post["title"]}</li>
    {/each}
</ul>
