<script>
    import {onMount} from "svelte";
    let users = [];

    onMount(async () => {
        const res = await fetch("/api/user/");
        users = res.json();
    });
    console.log(users);
</script>

<p>Hello!</p>