#!/usr/bin/env python3
"""Check generated Room schemas against the committed JSON files."""

from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]
BASELINE = ROOT / "app/schemas"
GENERATED = ROOT / "app/build/generated/roomSchemas"


def snapshots(directory: Path) -> dict[str, bytes]:
    return {
        str(path.relative_to(directory)): path.read_bytes()
        for path in directory.rglob("*.json")
    }


def main() -> int:
    baseline = snapshots(BASELINE)
    generated = snapshots(GENERATED)
    if not baseline or not generated:
        print("Missing Room schemas. Compile androidTest and retain the approved app/schemas baseline.")
        return 1
    changed = sorted(
        path for path in baseline.keys() | generated.keys()
        if baseline.get(path) != generated.get(path)
    )
    if changed:
        print("Room schema exports differ from the approved baseline:")
        print("\n".join(changed))
        return 1
    print("Room schema exports match the approved baseline.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
