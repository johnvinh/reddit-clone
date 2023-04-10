<script>
    import {onMount} from "svelte";
    let posts = [];

    onMount(async () => {
        const res = await fetch("/api/post");
        posts = await res.json();
    });
</script>

<ul>
    {#each posts as post}
        <li>{post["title"]}</li>
    {/each}
</ul>
